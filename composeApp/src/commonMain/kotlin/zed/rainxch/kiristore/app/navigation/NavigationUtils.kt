package zed.rainxch.kiristore.app.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute

fun NavBackStackEntry?.getCurrentScreen(): KiriStoreGraph? {
    if (this == null) return null
    val route = destination.route ?: return null

    return when {
        route.contains("HomeScreen") -> KiriStoreGraph.HomeScreen
        route.contains("SearchScreen") -> KiriStoreGraph.SearchScreen
        route.contains("AuthenticationScreen") -> KiriStoreGraph.AuthenticationScreen
        route.contains("DetailsScreen") -> toRoute<KiriStoreGraph.DetailsScreen>()
        route.contains("DeveloperProfileScreen") -> toRoute<KiriStoreGraph.DeveloperProfileScreen>()
        route.contains("ProfileScreen") -> KiriStoreGraph.ProfileScreen
        route.contains("TweaksScreen") -> KiriStoreGraph.TweaksScreen
        route.contains("RecentlyViewedScreen") -> KiriStoreGraph.RecentlyViewedScreen
        route.contains("FavouritesScreen") -> KiriStoreGraph.FavouritesScreen
        route.contains("StarredReposScreen") -> KiriStoreGraph.StarredReposScreen
        route.contains("AppsScreen") -> KiriStoreGraph.AppsScreen
        else -> null
    }
}

