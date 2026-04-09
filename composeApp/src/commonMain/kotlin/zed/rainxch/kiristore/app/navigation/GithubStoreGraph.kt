package zed.rainxch.kiristore.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface KiriStoreGraph {
    @Serializable
    data object HomeScreen : KiriStoreGraph

    @Serializable
    data object SearchScreen : KiriStoreGraph

    @Serializable
    data object AuthenticationScreen : KiriStoreGraph

    @Serializable
    data class DetailsScreen(
        val repositoryId: Long = -1L,
        val owner: String = "",
        val repo: String = "",
        val isComingFromUpdate: Boolean = false,
    ) : KiriStoreGraph

    @Serializable
    data class DeveloperProfileScreen(
        val username: String,
    ) : KiriStoreGraph

    @Serializable
    data object ProfileScreen : KiriStoreGraph

    @Serializable
    data object TweaksScreen : KiriStoreGraph

    @Serializable
    data object FavouritesScreen : KiriStoreGraph

    @Serializable
    data object StarredReposScreen : KiriStoreGraph

    @Serializable
    data object RecentlyViewedScreen : KiriStoreGraph

    @Serializable
    data object AppsScreen : KiriStoreGraph

    @Serializable
    data object SponsorScreen : KiriStoreGraph
}
