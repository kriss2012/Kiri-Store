package zed.rainxch.auth.presentation

import zed.rainxch.auth.presentation.model.GithubDeviceStartUi

sealed interface AuthenticationAction {
    data object StartLogin : AuthenticationAction

    data class CopyCode(
        val start: GithubDeviceStartUi,
    ) : AuthenticationAction

    data class OpenGitHub(
        val start: GithubDeviceStartUi,
    ) : AuthenticationAction

    data object MarkLoggedOut : AuthenticationAction

    data object MarkLoggedIn : AuthenticationAction

    data class OnInfo(
        val message: String,
    ) : AuthenticationAction

    data object SkipLogin : AuthenticationAction

    data object PollNow : AuthenticationAction

    data object OnResumed : AuthenticationAction
}
