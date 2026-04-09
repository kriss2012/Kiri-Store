package zed.rainxch.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import zed.rainxch.core.domain.logging.KiriStoreLogger
import zed.rainxch.core.domain.model.Platform
import zed.rainxch.core.domain.model.RateLimitException
import zed.rainxch.core.domain.repository.FavouritesRepository
import zed.rainxch.core.domain.repository.InstalledAppsRepository
import zed.rainxch.core.domain.repository.SearchHistoryRepository
import zed.rainxch.core.domain.repository.SeenReposRepository
import zed.rainxch.core.domain.repository.StarredRepository
import zed.rainxch.core.domain.repository.TweaksRepository
import zed.rainxch.core.domain.use_cases.SyncInstalledAppsUseCase
import zed.rainxch.core.domain.utils.ClipboardHelper
import zed.rainxch.core.domain.utils.ShareManager
import zed.rainxch.core.presentation.model.DiscoveryRepositoryUi
import zed.rainxch.core.presentation.utils.toUi
import zed.rainxch.domain.repository.SearchRepository
import zed.rainxch.kiristore.core.presentation.res.Res
import zed.rainxch.kiristore.core.presentation.res.failed_to_share_link
import zed.rainxch.kiristore.core.presentation.res.link_copied_to_clipboard
import zed.rainxch.kiristore.core.presentation.res.no_github_link_in_clipboard
import zed.rainxch.kiristore.core.presentation.res.no_repositories_found
import zed.rainxch.kiristore.core.presentation.res.search_failed
import zed.rainxch.search.presentation.mappers.toDomain
import zed.rainxch.search.presentation.utils.isEntirelyGithubUrls
import zed.rainxch.search.presentation.utils.parseGithubUrls

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val installedAppsRepository: InstalledAppsRepository,
    private val syncInstalledAppsUseCase: SyncInstalledAppsUseCase,
    private val favouritesRepository: FavouritesRepository,
    private val starredRepository: StarredRepository,
    private val logger: KiriStoreLogger,
    private val shareManager: ShareManager,
    private val platform: Platform,
    private val clipboardHelper: ClipboardHelper,
    private val tweaksRepository: TweaksRepository,
    private val seenReposRepository: SeenReposRepository,
    private val searchHistoryRepository: SearchHistoryRepository,
) : ViewModel() {
    private var hasLoadedInitialData = false
    private var currentSearchJob: Job? = null
    private var currentPage = 1

    companion object {
        private const val MIN_QUERY_LENGTH = 3
    }

    private val _state = MutableStateFlow(SearchState())
    val state =
        _state
            .onStart {
                if (!hasLoadedInitialData) {
                    syncSystemState()

                    observeInstalledApps()
                    observeFavouriteApps()
                    observeStarredRepos()
                    observeLiquidGlassEnabled()
                    observeSeenRepos()
                    observeHideSeenEnabled()
                    observeClipboardSetting()
                    observeSearchHistory()
                    checkClipboardForLinks()

                    hasLoadedInitialData = true
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = SearchState(),
            )

    private fun observeLiquidGlassEnabled() {
        viewModelScope.launch {
            tweaksRepository.getLiquidGlassEnabled().collect { enabled ->
                _state.update {
                    it.copy(
                        isLiquidGlassEnabled = enabled,
                    )
                }
            }
        }
    }

    private fun observeSeenRepos() {
        viewModelScope.launch {
            seenReposRepository.getAllSeenRepoIds().collect { ids ->
                _state.update { current ->
                    current.copy(
                        seenRepoIds = ids,
                        repositories =
                            current.repositories
                                .map { repo ->
                                    repo.copy(isSeen = repo.repository.id in ids)
                                }.toImmutableList(),
                    )
                }
            }
        }
    }

    private fun observeHideSeenEnabled() {
        viewModelScope.launch {
            tweaksRepository.getHideSeenEnabled().collect { enabled ->
                _state.update { it.copy(isHideSeenEnabled = enabled) }
            }
        }
    }

    private val _events = Channel<SearchEvent>()
    val events = _events.receiveAsFlow()

    private fun syncSystemState() {
        viewModelScope.launch {
            try {
                val result = syncInstalledAppsUseCase()
                if (result.isFailure) {
                    logger.warn("Initial sync had issues: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                logger.error("Initial sync failed: ${e.message}")
            }
        }
    }

    private fun observeClipboardSetting() {
        viewModelScope.launch {
            tweaksRepository.getAutoDetectClipboardLinks().collect { enabled ->
                _state.update { current ->
                    current.copy(
                        autoDetectClipboardEnabled = enabled,
                        clipboardLinks = if (enabled) current.clipboardLinks else persistentListOf(),
                        isClipboardBannerVisible = if (enabled) current.isClipboardBannerVisible else false,
                    )
                }
                if (enabled) checkClipboardForLinks()
            }
        }
    }

    private fun checkClipboardForLinks() {
        viewModelScope.launch {
            val enabled = tweaksRepository.getAutoDetectClipboardLinks().first()
            if (!enabled) return@launch

            try {
                val clipText = clipboardHelper.getText() ?: return@launch
                val links = parseGithubUrls(clipText).toImmutableList()
                if (links.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            clipboardLinks = links,
                            isClipboardBannerVisible = true,
                        )
                    }
                }
            } catch (e: Exception) {
                logger.debug("Failed to read clipboard: ${e.message}")
            }
        }
    }

    private fun observeSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.getRecentSearches().collect { searches ->
                _state.update {
                    it.copy(recentSearches = searches.toImmutableList())
                }
            }
        }
    }

    private fun saveSearchToHistory(query: String) {
        val trimmed = query.trim()
        if (trimmed.length < MIN_QUERY_LENGTH) return
        viewModelScope.launch {
            searchHistoryRepository.addSearch(trimmed)
        }
    }

    private fun observeInstalledApps() {
        viewModelScope.launch {
            installedAppsRepository
                .getAllInstalledApps()
                .collect { installedApps ->
                    val installedMap = installedApps.associateBy { it.repoId }
                    _state.update { current ->
                        current.copy(
                            repositories =
                                current.repositories
                                    .map { searchRepo ->
                                        val app = installedMap[searchRepo.repository.id]
                                        searchRepo.copy(
                                            isInstalled = app != null,
                                            isUpdateAvailable = app?.isUpdateAvailable ?: false,
                                        )
                                    }.toImmutableList(),
                        )
                    }
                }
        }
    }

    private fun observeFavouriteApps() {
        viewModelScope.launch {
            favouritesRepository.getAllFavorites().collect { favoriteRepos ->
                val installedMap = favoriteRepos.associateBy { it.repoId }
                _state.update { current ->
                    current.copy(
                        repositories =
                            current.repositories
                                .map { searchRepo ->
                                    val app = installedMap[searchRepo.repository.id]
                                    searchRepo.copy(
                                        isFavourite = app != null,
                                    )
                                }.toImmutableList(),
                    )
                }
            }
        }
    }

    private fun observeStarredRepos() {
        viewModelScope.launch {
            starredRepository.getAllStarred().collect { starredRepos ->
                val installedMap = starredRepos.associateBy { it.repoId }
                _state.update { current ->
                    current.copy(
                        repositories =
                            current.repositories
                                .map { searchRepo ->
                                    val app = installedMap[searchRepo.repository.id]
                                    searchRepo.copy(isStarred = app != null)
                                }.toImmutableList(),
                    )
                }
            }
        }
    }

    private fun performSearch(isInitial: Boolean = false) {
        val query = _state.value.query.trim()
        if (query.isBlank() || query.length < MIN_QUERY_LENGTH) {
            if (query.isBlank()) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        repositories = persistentListOf(),
                        errorMessage = null,
                        totalCount = null,
                    )
                }
            }
            return
        }

        if (isInitial) {
            currentSearchJob?.cancel()
            currentPage = 1
        }

        currentSearchJob =
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        isLoading = isInitial,
                        isLoadingMore = !isInitial,
                        errorMessage = null,
                        repositories =
                            if (isInitial) {
                                persistentListOf()
                            } else {
                                it.repositories
                            },
                        totalCount = if (isInitial) null else it.totalCount,
                    )
                }

                try {
                    val installedMap =
                        installedAppsRepository
                            .getAllInstalledApps()
                            .first()
                            .associateBy { it.repoId }
                    val favoritesMap =
                        favouritesRepository
                            .getAllFavorites()
                            .first()
                            .associateBy { it.repoId }
                    val starredReposMap =
                        starredRepository
                            .getAllStarred()
                            .first()
                            .associateBy { it.repoId }

                    searchRepository
                        .searchRepositories(
                            query = _state.value.query,
                            platform = _state.value.selectedSearchPlatform.toDomain(),
                            language = _state.value.selectedLanguage.toDomain(),
                            sortBy = _state.value.selectedSortBy.toDomain(),
                            sortOrder = _state.value.selectedSortOrder.toDomain(),
                            page = currentPage,
                        ).collect { paginatedRepos ->
                            currentPage = paginatedRepos.nextPageIndex

                            val seenIds = _state.value.seenRepoIds

                            val newReposWithStatus =
                                paginatedRepos.repos.map { repo ->
                                    val app = installedMap[repo.id]
                                    val favourite = favoritesMap[repo.id]
                                    val starred = starredReposMap[repo.id]

                                    DiscoveryRepositoryUi(
                                        isInstalled = app != null,
                                        isFavourite = favourite != null,
                                        isStarred = starred != null,
                                        isSeen = repo.id in seenIds,
                                        isUpdateAvailable = app?.isUpdateAvailable ?: false,
                                        repository = repo.toUi(),
                                    )
                                }

                            _state.update { currentState ->
                                val mergedMap = LinkedHashMap<Long, DiscoveryRepositoryUi>()

                                currentState.repositories.forEach { r ->
                                    mergedMap[r.repository.id] = r
                                }

                                newReposWithStatus.forEach { r ->
                                    val existing = mergedMap[r.repository.id]
                                    if (existing == null) {
                                        mergedMap[r.repository.id] = r
                                    } else {
                                        mergedMap[r.repository.id] =
                                            existing.copy(
                                                isInstalled = r.isInstalled,
                                                isUpdateAvailable = r.isUpdateAvailable,
                                                isFavourite = r.isFavourite,
                                                isStarred = r.isStarred,
                                                repository = r.repository,
                                            )
                                    }
                                }

                                val allRepos = mergedMap.values.toImmutableList()

                                currentState.copy(
                                    repositories = allRepos,
                                    hasMorePages = paginatedRepos.hasMore,
                                    totalCount = allRepos.size,
                                    errorMessage =
                                        if (allRepos.isEmpty() && !paginatedRepos.hasMore) {
                                            getString(Res.string.no_repositories_found)
                                        } else {
                                            null
                                        },
                                )
                            }
                        }

                    _state.update {
                        it.copy(isLoading = false, isLoadingMore = false)
                    }
                } catch (e: RateLimitException) {
                    logger.debug("Rate limit exceeded: ${e.message}")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            errorMessage = e.message,
                        )
                    }
                } catch (e: CancellationException) {
                    logger.debug("Search cancelled (expected): ${e.message}")
                } catch (e: Exception) {
                    logger.error("Search failed: ${e.message}")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            errorMessage = e.message ?: getString(Res.string.search_failed),
                        )
                    }
                }
            }
    }

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnPlatformTypeSelected -> {
                if (_state.value.selectedSearchPlatform != action.searchPlatform) {
                    _state.update {
                        it.copy(selectedSearchPlatform = action.searchPlatform)
                    }
                    currentPage = 1
    
                    performSearch(isInitial = true)
                }
            }

            is SearchAction.OnLanguageSelected -> {
                if (_state.value.selectedLanguage != action.language) {
                    _state.update {
                        it.copy(selectedLanguage = action.language)
                    }
                    currentPage = 1
    
                    performSearch(isInitial = true)
                }
            }

            is SearchAction.OnSearchChange -> {
                val links = parseGithubUrls(action.query)
                _state.update {
                    it.copy(
                        query = action.query,
                        detectedLinks = links,
                    )
                }

                if (action.query.isBlank()) {
                    currentSearchJob?.cancel()
                    _state.update {
                        it.copy(
                            repositories = persistentListOf(),
                            isLoading = false,
                            isLoadingMore = false,
                            errorMessage = null,
                            totalCount = null,
                        )
                    }
                } else if (isEntirelyGithubUrls(action.query)) {
                    currentSearchJob?.cancel()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            errorMessage = null,
                            repositories = persistentListOf(),
                            totalCount = null,
                        )
                    }
                }
            }

            SearchAction.OnToggleLanguageSheetVisibility -> {
                _state.update {
                    it.copy(isLanguageSheetVisible = !it.isLanguageSheetVisible)
                }
            }

            SearchAction.OnSearchImeClick -> {
                if (_state.value.detectedLinks.isNotEmpty() && isEntirelyGithubUrls(_state.value.query)) {
                    val link = _state.value.detectedLinks.first()
                    viewModelScope.launch {
                        _events.send(SearchEvent.NavigateToRepo(link.owner, link.repo))
                    }
                    return
                }

                currentPage = 1
                saveSearchToHistory(_state.value.query)
                performSearch(isInitial = true)
            }

            is SearchAction.OnShareClick -> {
                viewModelScope.launch {
                    runCatching {
                        shareManager.shareText("https://github.com/${action.repo.fullName}")
                    }.onFailure { t ->
                        logger.error("Failed to share link: ${t.message}")
                        _events.send(
                            SearchEvent.OnMessage(getString(Res.string.failed_to_share_link)),
                        )
                        return@launch
                    }

                    if (platform != Platform.ANDROID) {
                        _events.send(SearchEvent.OnMessage(getString(Res.string.link_copied_to_clipboard)))
                    }
                }
            }

            is SearchAction.OnSortBySelected -> {
                if (_state.value.selectedSortBy != action.sortBy) {
                    _state.update {
                        it.copy(selectedSortBy = action.sortBy)
                    }
                    currentPage = 1
    
                    performSearch(isInitial = true)
                }
            }

            is SearchAction.OnSortOrderSelected -> {
                if (_state.value.selectedSortOrder != action.sortOrder) {
                    _state.update {
                        it.copy(selectedSortOrder = action.sortOrder)
                    }
                    currentPage = 1
    
                    performSearch(isInitial = true)
                }
            }

            SearchAction.OnToggleSortByDialogVisibility -> {
                _state.update {
                    it.copy(isSortByDialogVisible = !it.isSortByDialogVisible)
                }
            }

            SearchAction.LoadMore -> {
                if (!_state.value.isLoadingMore && !_state.value.isLoading && _state.value.hasMorePages) {
                    performSearch(isInitial = false)
                }
            }

            SearchAction.Retry -> {
                currentPage = 1

                performSearch(isInitial = true)
            }

            SearchAction.OnClearClick -> {
                _state.update {
                    it.copy(
                        query = "",
                        repositories = persistentListOf(),
                        isLoading = false,
                        isLoadingMore = false,
                        errorMessage = null,
                        totalCount = null,
                        detectedLinks = persistentListOf(),
                    )
                }
            }

            is SearchAction.OpenGithubLink -> {
                viewModelScope.launch {
                    _events.send(SearchEvent.NavigateToRepo(action.owner, action.repo))
                }
            }

            SearchAction.OnFabClick -> {
                viewModelScope.launch {
                    try {
                        val clipText = clipboardHelper.getText()
                        if (clipText.isNullOrBlank()) {
                            _events.send(SearchEvent.OnMessage(getString(Res.string.no_github_link_in_clipboard)))
                            return@launch
                        }
                        val links = parseGithubUrls(clipText)
                        if (links.isEmpty()) {
                            _events.send(SearchEvent.OnMessage(getString(Res.string.no_github_link_in_clipboard)))
                            return@launch
                        }
                        if (links.size == 1) {
                            _events.send(
                                SearchEvent.NavigateToRepo(
                                    links.first().owner,
                                    links.first().repo,
                                ),
                            )
                        } else {
                            _state.update {
                                it.copy(
                                    query = clipText,
                                    detectedLinks = links,
                                    repositories = persistentListOf(),
                                    totalCount = null,
                                    isLoading = false,
                                    isLoadingMore = false,
                                    errorMessage = null,
                                )
                            }
                        }
                    } catch (e: Exception) {
                        logger.error("Failed to read clipboard: ${e.message}")
                        _events.send(SearchEvent.OnMessage(getString(Res.string.no_github_link_in_clipboard)))
                    }
                }
            }

            SearchAction.DismissClipboardBanner -> {
                _state.update {
                    it.copy(isClipboardBannerVisible = false)
                }
            }

            is SearchAction.OnRepositoryClick -> {
                // Handled in composable
            }

            SearchAction.OnNavigateBackClick -> {
                // Handled in composable
            }

            is SearchAction.OnRepositoryDeveloperClick -> {
                // Handled in composable
            }

            is SearchAction.OnHistoryItemClick -> {
                _state.update {
                    it.copy(
                        query = action.query,
                        detectedLinks = persistentListOf(),
                    )
                }

                currentPage = 1
                saveSearchToHistory(action.query)
                performSearch(isInitial = true)
            }

            is SearchAction.OnRemoveHistoryItem -> {
                viewModelScope.launch {
                    searchHistoryRepository.removeSearch(action.query)
                }
            }

            SearchAction.OnClearAllHistory -> {
                viewModelScope.launch {
                    searchHistoryRepository.clearAll()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentSearchJob?.cancel()
    }
}

