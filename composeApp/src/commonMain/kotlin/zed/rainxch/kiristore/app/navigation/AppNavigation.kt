package zed.rainxch.kiristore.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import io.github.fletchmckee.liquid.rememberLiquidState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import zed.rainxch.apps.presentation.AppsRoot
import zed.rainxch.apps.presentation.AppsViewModel
import zed.rainxch.auth.presentation.AuthenticationRoot
import zed.rainxch.core.presentation.locals.LocalBottomNavigationHeight
import zed.rainxch.core.presentation.locals.LocalBottomNavigationLiquid
import zed.rainxch.core.presentation.locals.LocalScrollbarEnabled
import zed.rainxch.details.presentation.DetailsRoot
import zed.rainxch.devprofile.presentation.DeveloperProfileRoot
import zed.rainxch.favourites.presentation.FavouritesRoot
import zed.rainxch.home.presentation.HomeRoot
import zed.rainxch.profile.presentation.ProfileRoot
import zed.rainxch.profile.presentation.SponsorScreen
import zed.rainxch.recentlyviewed.presentation.RecentlyViewedRoot
import zed.rainxch.search.presentation.SearchRoot
import zed.rainxch.starred.presentation.StarredReposRoot
import zed.rainxch.tweaks.presentation.TweaksRoot

@Composable
fun AppNavigation(
    navController: NavHostController,
    isLiquidGlassEnabled: Boolean = true,
    isScrollbarEnabled: Boolean = false,
) {
    val liquidState = rememberLiquidState()
    var bottomNavigationHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val appsViewModel = koinViewModel<AppsViewModel>()
    val appsState by appsViewModel.state.collectAsStateWithLifecycle()

    CompositionLocalProvider(
        LocalBottomNavigationLiquid provides liquidState,
        LocalBottomNavigationHeight provides bottomNavigationHeight,
        LocalScrollbarEnabled provides isScrollbarEnabled,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            NavHost(
                navController = navController,
                startDestination = KiriStoreGraph.HomeScreen,
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
            ) {
                composable<KiriStoreGraph.HomeScreen> {
                    HomeRoot(
                        onNavigateToSearch = {
                            navController.navigate(KiriStoreGraph.SearchScreen)
                        },
                        onNavigateToSettings = {
                            navController.navigate(KiriStoreGraph.ProfileScreen)
                        },
                        onNavigateToApps = {
                            navController.navigate(KiriStoreGraph.AppsScreen)
                        },
                        onNavigateToDetails = { repoId ->
                            navController.navigate(
                                KiriStoreGraph.DetailsScreen(
                                    repositoryId = repoId,
                                ),
                            )
                        },
                        onNavigateToDeveloperProfile = { username ->
                            navController.navigate(
                                KiriStoreGraph.DeveloperProfileScreen(
                                    username = username,
                                ),
                            )
                        },
                    )
                }

                composable<KiriStoreGraph.SearchScreen> {
                    SearchRoot(
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                        onNavigateToDetails = { repoId ->
                            navController.navigate(
                                KiriStoreGraph.DetailsScreen(
                                    repositoryId = repoId,
                                ),
                            )
                        },
                        onNavigateToDetailsFromLink = { owner, repo ->
                            navController.navigate(
                                KiriStoreGraph.DetailsScreen(
                                    owner = owner,
                                    repo = repo,
                                ),
                            )
                        },
                        onNavigateToDeveloperProfile = { username ->
                            navController.navigate(
                                KiriStoreGraph.DeveloperProfileScreen(
                                    username = username,
                                ),
                            )
                        },
                    )
                }

                composable<KiriStoreGraph.DetailsScreen> { backStackEntry ->
                    val args = backStackEntry.toRoute<KiriStoreGraph.DetailsScreen>()
                    DetailsRoot(
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                        onOpenRepositoryInApp = { repoId ->
                            navController.navigate(
                                KiriStoreGraph.DetailsScreen(
                                    repositoryId = repoId,
                                ),
                            )
                        },
                        onNavigateToDeveloperProfile = { username ->
                            navController.navigate(
                                KiriStoreGraph.DeveloperProfileScreen(
                                    username = username,
                                ),
                            )
                        },
                        viewModel =
                            koinViewModel {
                                parametersOf(
                                    args.repositoryId,
                                    args.owner,
                                    args.repo,
                                    args.isComingFromUpdate,
                                )
                            },
                    )
                }

                composable<KiriStoreGraph.DeveloperProfileScreen> { backStackEntry ->
                    val args = backStackEntry.toRoute<KiriStoreGraph.DeveloperProfileScreen>()
                    DeveloperProfileRoot(
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                        onNavigateToDetails = { repoId ->
                            navController.navigate(
                                KiriStoreGraph.DetailsScreen(
                                    repositoryId = repoId,
                                ),
                            )
                        },
                        viewModel =
                            koinViewModel {
                                parametersOf(args.username)
                            },
                    )
                }

                composable<KiriStoreGraph.AuthenticationScreen> {
                    AuthenticationRoot(
                        onNavigateToHome = {
                            navController.navigate(KiriStoreGraph.HomeScreen) {
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }
                        },
                    )
                }

                composable<KiriStoreGraph.FavouritesScreen> {
                    FavouritesRoot(
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                        onNavigateToDetails = {
                            navController.navigate(KiriStoreGraph.DetailsScreen(it))
                        },
                        onNavigateToDeveloperProfile = { username ->
                            navController.navigate(
                                KiriStoreGraph.DeveloperProfileScreen(
                                    username = username,
                                ),
                            )
                        },
                    )
                }

                composable<KiriStoreGraph.StarredReposScreen> {
                    StarredReposRoot(
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                        onNavigateToDetails = { repoId ->
                            navController.navigate(
                                KiriStoreGraph.DetailsScreen(
                                    repositoryId = repoId,
                                ),
                            )
                        },
                        onNavigateToAuthentication = {
                            navController.navigate(
                                KiriStoreGraph.AuthenticationScreen,
                            )
                        },
                        onNavigateToDeveloperProfile = { username ->
                            navController.navigate(
                                KiriStoreGraph.DeveloperProfileScreen(
                                    username = username,
                                ),
                            )
                        },
                    )
                }

                composable<KiriStoreGraph.ProfileScreen> {
                    ProfileRoot(
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                        onNavigateToAuthentication = {
                            navController.navigate(KiriStoreGraph.AuthenticationScreen)
                        },
                        onNavigateToStarredRepos = {
                            navController.navigate(KiriStoreGraph.StarredReposScreen)
                        },
                        onNavigateToFavouriteRepos = {
                            navController.navigate(KiriStoreGraph.FavouritesScreen)
                        },
                        onNavigateToRecentlyViewed = {
                            navController.navigate(KiriStoreGraph.RecentlyViewedScreen)
                        },
                        onNavigateToDevProfile = { username ->
                            navController.navigate(KiriStoreGraph.DeveloperProfileScreen(username))
                        },
                        onNavigateToSponsor = {
                            navController.navigate(KiriStoreGraph.SponsorScreen)
                        },
                    )
                }

                composable<KiriStoreGraph.RecentlyViewedScreen> {
                    RecentlyViewedRoot(
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                        onNavigateToDetails = { repoId ->
                            navController.navigate(
                                KiriStoreGraph.DetailsScreen(
                                    repositoryId = repoId,
                                ),
                            )
                        },
                        onNavigateToDeveloperProfile = { username ->
                            navController.navigate(
                                KiriStoreGraph.DeveloperProfileScreen(
                                    username = username,
                                ),
                            )
                        },
                    )
                }

                composable<KiriStoreGraph.SponsorScreen> {
                    SponsorScreen(
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                    )
                }

                composable<KiriStoreGraph.TweaksScreen> {
                    TweaksRoot()
                }

                composable<KiriStoreGraph.AppsScreen> {
                    AppsRoot(
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                        onNavigateToRepo = { repoId ->
                            navController.navigate(
                                KiriStoreGraph.DetailsScreen(
                                    repositoryId = repoId,
                                    isComingFromUpdate = true,
                                ),
                            )
                        },
                        viewModel = appsViewModel,
                        state = appsState,
                    )
                }
            }

            val currentScreen =
                navController.currentBackStackEntryAsState().value.getCurrentScreen()

            currentScreen?.let {
                BottomNavigation(
                    currentScreen = currentScreen,
                    onNavigate = {
                        navController.navigate(it) {
                            popUpTo(KiriStoreGraph.HomeScreen) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    isUpdateAvailable = appsState.apps.any { it.installedApp.isUpdateAvailable },
                    isLiquidGlassEnabled = isLiquidGlassEnabled,
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 24.dp)
                            .onGloballyPositioned { coordinates ->
                                bottomNavigationHeight =
                                    with(density) { coordinates.size.height.toDp() }
                            },
                )
            }
        }
    }
}

