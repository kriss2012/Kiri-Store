package zed.rainxch.auth.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.getString
import zed.rainxch.auth.domain.repository.AuthenticationRepository
import zed.rainxch.auth.domain.repository.DevicePollResult
import zed.rainxch.auth.presentation.mapper.toUi
import zed.rainxch.auth.presentation.model.AuthLoginState
import zed.rainxch.auth.presentation.model.GithubDeviceStartUi
import zed.rainxch.core.domain.logging.KiriStoreLogger
import zed.rainxch.core.domain.utils.BrowserHelper
import zed.rainxch.core.domain.utils.ClipboardHelper
import zed.rainxch.kiristore.core.presentation.res.*

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val browserHelper: BrowserHelper,
    private val clipboardHelper: ClipboardHelper,
    private val scope: CoroutineScope,
    private val logger: KiriStoreLogger,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var hasLoadedInitialData = false
    private var countdownJob: Job? = null
    private var pollingJob: Job? = null
    private var pollingIntervalMs: Long = DEFAULT_POLL_INTERVAL_SEC * 1000L

    private val _state: MutableStateFlow<AuthenticationState> =
        MutableStateFlow(AuthenticationState())

    private val _events = Channel<AuthenticationEvents>(capacity = Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    val state =
        _state
            .onStart {
                if (!hasLoadedInitialData) {
                    scope.launch {
                        authenticationRepository.accessTokenFlow.collect { token ->
                            _state.update {
                                it.copy(
                                    loginState =
                                        if (token.isNullOrEmpty()) {
                                            AuthLoginState.LoggedOut
                                        } else {
                                            _events.trySend(AuthenticationEvents.OnNavigateToMain)
                                            AuthLoginState.LoggedIn
                                        },
                                )
                            }
                        }
                    }

                    restoreFromSavedState()
                    hasLoadedInitialData = true
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = AuthenticationState(),
            )

    fun onAction(action: AuthenticationAction) {
        when (action) {
            is AuthenticationAction.StartLogin -> {
                startLogin()
            }

            is AuthenticationAction.CopyCode -> {
                copyCode(action.start)
            }

            is AuthenticationAction.OpenGitHub -> {
                openGitHub(action.start)
            }

            AuthenticationAction.MarkLoggedIn -> {
                _state.update { it.copy(loginState = AuthLoginState.LoggedIn) }
            }

            AuthenticationAction.MarkLoggedOut -> {
                _state.update { it.copy(loginState = AuthLoginState.LoggedOut) }
            }

            is AuthenticationAction.OnInfo -> {
                _state.update {
                    it.copy(
                        info = action.message,
                    )
                }
            }

            AuthenticationAction.SkipLogin -> {
                _events.trySend(AuthenticationEvents.OnNavigateToMain)
            }

            AuthenticationAction.PollNow -> {
                forcePollNow()
            }

            AuthenticationAction.OnResumed -> {
                tryPollIfReady()
            }
        }
    }

    private fun startCountdown(remainingSeconds: Int) {
        countdownJob?.cancel()
        countdownJob =
            viewModelScope.launch {
                var remaining = remainingSeconds
                while (remaining > 0) {
                    _state.update { currentState ->
                        val loginState = currentState.loginState
                        if (loginState is AuthLoginState.DevicePrompt) {
                            currentState.copy(
                                loginState = loginState.copy(remainingSeconds = remaining),
                            )
                        } else {
                            return@launch
                        }
                    }
                    delay(1000L)
                    remaining--
                }

                pollingJob?.cancel()
                clearSavedState()
                _state.update {
                    it.copy(
                        loginState =
                            AuthLoginState.Error(
                                message = getString(Res.string.auth_error_code_expired),
                                recoveryHint = getString(Res.string.auth_hint_try_again),
                            ),
                    )
                }
            }
    }

    private fun tryPollIfReady() {
        val loginState = _state.value.loginState
        if (loginState is AuthLoginState.DevicePrompt && !_state.value.isPolling) {
            pollOnce(loginState.start.deviceCode)
        }
    }

    private fun forcePollNow() {
        val loginState = _state.value.loginState
        if (loginState is AuthLoginState.DevicePrompt) {
            val deviceCode = loginState.start.deviceCode
            logger.debug("Manual poll requested (isPolling=${_state.value.isPolling}, pollingJobActive=${pollingJob?.isActive})")
            // Restart background polling if it died
            if (pollingJob?.isActive != true) {
                logger.debug("Polling job was dead — restarting background polling")
                startPolling(deviceCode)
            }
            pollOnce(deviceCode)
        }
    }

    private fun startLogin() {
        viewModelScope.launch {
            try {
                val start =
                    withContext(Dispatchers.IO) {
                        authenticationRepository.startDeviceFlow()
                    }

                val startUi = start.toUi()

                withContext(Dispatchers.Main.immediate) {
                    _state.update {
                        it.copy(
                            loginState =
                                AuthLoginState.DevicePrompt(
                                    start = startUi,
                                    remainingSeconds = start.expiresInSec,
                                ),
                            copied = false,
                        )
                    }

                    saveToSavedState(start.deviceCode, startUi)
                    startCountdown(start.expiresInSec)
                    startPolling(start.deviceCode)

                    try {
                        clipboardHelper.copy(
                            label = getString(Res.string.enter_code_on_github),
                            text = start.userCode,
                        )
                        _state.update { it.copy(copied = true) }
                    } catch (e: Exception) {
                        logger.debug("Failed to copy to clipboard: ${e.message}")
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (t: Throwable) {
                countdownJob?.cancel()
                pollingJob?.cancel()
                clearSavedState()
                val (message, hint) = categorizeError(t)
                withContext(Dispatchers.Main.immediate) {
                    _state.update {
                        it.copy(
                            loginState =
                                AuthLoginState.Error(
                                    message = message,
                                    recoveryHint = hint,
                                ),
                        )
                    }
                }
            }
        }
    }

    private fun startPolling(deviceCode: String) {
        pollingJob?.cancel()
        val loginState = _state.value.loginState
        val intervalSec =
            (loginState as? AuthLoginState.DevicePrompt)?.start?.intervalSec
                ?: DEFAULT_POLL_INTERVAL_SEC
        // Add 1s buffer above GitHub's minimum to avoid immediate slow_down
        pollingIntervalMs = (intervalSec * 1000).toLong() + 1000L
        pollingJob =
            viewModelScope.launch {
                while (isActive) {
                    delay(pollingIntervalMs)
                    doPoll(deviceCode)
                }
            }
    }

    private fun pollOnce(deviceCode: String) {
        viewModelScope.launch {
            doPoll(deviceCode)
        }
    }

    private suspend fun doPoll(deviceCode: String) {
        _state.update { it.copy(isPolling = true) }
        try {
            logger.debug("Polling device token (code=${deviceCode.take(8)}..., interval=${pollingIntervalMs}ms)")
            val result =
                withContext(Dispatchers.IO) {
                    authenticationRepository.pollDeviceTokenOnce(deviceCode)
                }

            when (result) {
                is DevicePollResult.Success -> {
                    logger.debug("Poll success — token received, navigating")
                    pollingJob?.cancel()
                    countdownJob?.cancel()
                    clearSavedState()
                    _state.update {
                        it.copy(loginState = AuthLoginState.LoggedIn, isPolling = false)
                    }
                    _events.trySend(AuthenticationEvents.OnNavigateToMain)
                }

                is DevicePollResult.Pending -> {
                    logger.debug("Poll result: still pending")
                    _state.update { it.copy(isPolling = false, pollIntervalSec = 0) }
                }

                is DevicePollResult.SlowDown -> {
                    pollingIntervalMs += 5000L
                    logger.debug("Poll result: slow_down — increased interval to ${pollingIntervalMs}ms")
                    _state.update {
                        it.copy(
                            isPolling = false,
                            pollIntervalSec = (pollingIntervalMs / 1000).toInt(),
                        )
                    }
                    // Don't restart — the existing polling loop reads pollingIntervalMs
                    // on each iteration via delay(), so it will pick up the new value.
                }

                is DevicePollResult.Failed -> {
                    logger.debug("Poll failed terminally: ${result.error.message}")
                    pollingJob?.cancel()
                    countdownJob?.cancel()
                    clearSavedState()
                    val (message, hint) = categorizeError(result.error)
                    _state.update {
                        it.copy(
                            loginState =
                                AuthLoginState.Error(message = message, recoveryHint = hint),
                            isPolling = false,
                        )
                    }
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (t: Throwable) {
            _state.update { it.copy(isPolling = false) }
            logger.debug("Unexpected poll error: ${t.message}")
        }
    }

    // region SavedStateHandle

    private fun saveToSavedState(
        deviceCode: String,
        startUi: GithubDeviceStartUi,
    ) {
        savedStateHandle[KEY_DEVICE_CODE] = deviceCode
        savedStateHandle[KEY_USER_CODE] = startUi.userCode
        savedStateHandle[KEY_VERIFICATION_URI] = startUi.verificationUri
        savedStateHandle[KEY_VERIFICATION_URI_COMPLETE] = startUi.verificationUriComplete
        savedStateHandle[KEY_INTERVAL_SEC] = startUi.intervalSec
        savedStateHandle[KEY_EXPIRES_IN_SEC] = startUi.expiresInSec
        savedStateHandle[KEY_START_TIME_MILLIS] = System.currentTimeMillis()
    }

    private fun clearSavedState() {
        SAVED_STATE_KEYS.forEach { savedStateHandle.remove<Any>(it) }
    }

    private fun restoreFromSavedState() {
        val deviceCode = savedStateHandle.get<String>(KEY_DEVICE_CODE) ?: return
        val userCode = savedStateHandle.get<String>(KEY_USER_CODE) ?: return
        val verificationUri = savedStateHandle.get<String>(KEY_VERIFICATION_URI) ?: return
        val expiresInSec = savedStateHandle.get<Int>(KEY_EXPIRES_IN_SEC) ?: return
        val intervalSec = savedStateHandle.get<Int>(KEY_INTERVAL_SEC) ?: 5
        val startTimeMillis = savedStateHandle.get<Long>(KEY_START_TIME_MILLIS) ?: return

        val elapsedSec = ((System.currentTimeMillis() - startTimeMillis) / 1000).toInt()
        val remainingSec = expiresInSec - elapsedSec

        if (remainingSec <= 0) {
            clearSavedState()
            return
        }

        val startUi =
            GithubDeviceStartUi(
                deviceCode = deviceCode,
                userCode = userCode,
                verificationUri = verificationUri,
                verificationUriComplete = savedStateHandle.get<String>(KEY_VERIFICATION_URI_COMPLETE),
                intervalSec = intervalSec,
                expiresInSec = expiresInSec,
            )

        _state.update {
            it.copy(loginState = AuthLoginState.DevicePrompt(startUi, remainingSec))
        }

        startCountdown(remainingSec)
        startPolling(deviceCode)
        pollOnce(deviceCode)
    }

    // endregion

    private suspend fun categorizeError(t: Throwable): Pair<String, String?> {
        val msg = t.message ?: return getString(Res.string.error_unknown) to null
        val lowerMsg = msg.lowercase()
        return when {
            "timeout" in lowerMsg || "timed out" in lowerMsg -> {
                msg to getString(Res.string.auth_hint_check_connection)
            }

            "network" in lowerMsg || "unresolvedaddress" in lowerMsg || "connect" in lowerMsg -> {
                msg to getString(Res.string.auth_hint_check_connection)
            }

            "expired" in lowerMsg || "expire" in lowerMsg -> {
                msg to getString(Res.string.auth_hint_try_again)
            }

            "denied" in lowerMsg || "access_denied" in lowerMsg -> {
                msg to getString(Res.string.auth_hint_denied)
            }

            else -> {
                msg to null
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
        pollingJob?.cancel()
    }

    private fun openGitHub(start: GithubDeviceStartUi) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            try {
                val url = start.verificationUriComplete ?: start.verificationUri
                browserHelper.openUrl(url)
            } catch (e: Exception) {
                logger.debug("Failed to open browser: ${e.message}")
            }
        }
    }

    private fun copyCode(start: GithubDeviceStartUi) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            try {
                clipboardHelper.copy(
                    label = "GitHub Code",
                    text = start.userCode,
                )

                _state.update {
                    val currentRemaining =
                        (it.loginState as? AuthLoginState.DevicePrompt)?.remainingSeconds ?: 0

                    it.copy(
                        loginState = AuthLoginState.DevicePrompt(start, currentRemaining),
                        copied = true,
                    )
                }
            } catch (e: Exception) {
                logger.debug("Failed to copy to clipboard: ${e.message}")
                _state.update {
                    val currentRemaining =
                        (it.loginState as? AuthLoginState.DevicePrompt)?.remainingSeconds ?: 0

                    it.copy(
                        loginState = AuthLoginState.DevicePrompt(start, currentRemaining),
                        copied = false,
                    )
                }
            }
        }
    }

    companion object {
        private const val KEY_DEVICE_CODE = "auth_device_code"
        private const val KEY_USER_CODE = "auth_user_code"
        private const val KEY_VERIFICATION_URI = "auth_verification_uri"
        private const val KEY_VERIFICATION_URI_COMPLETE = "auth_verification_uri_complete"
        private const val KEY_INTERVAL_SEC = "auth_interval_sec"
        private const val KEY_EXPIRES_IN_SEC = "auth_expires_in_sec"
        private const val KEY_START_TIME_MILLIS = "auth_start_time_millis"
        private const val DEFAULT_POLL_INTERVAL_SEC = 5

        private val SAVED_STATE_KEYS =
            listOf(
                KEY_DEVICE_CODE,
                KEY_USER_CODE,
                KEY_VERIFICATION_URI,
                KEY_VERIFICATION_URI_COMPLETE,
                KEY_INTERVAL_SEC,
                KEY_EXPIRES_IN_SEC,
                KEY_START_TIME_MILLIS,
            )
    }
}

