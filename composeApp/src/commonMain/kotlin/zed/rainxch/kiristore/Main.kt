package zed.rainxch.kiristore

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import zed.rainxch.core.presentation.theme.KiriStoreTheme
import zed.rainxch.core.presentation.utils.ApplyAndroidSystemBars
import zed.rainxch.core.presentation.utils.ObserveAsEvents
import zed.rainxch.kiristore.app.components.RateLimitDialog
import zed.rainxch.kiristore.app.components.SessionExpiredDialog
import zed.rainxch.kiristore.app.deeplink.DeepLinkDestination
import zed.rainxch.kiristore.app.deeplink.DeepLinkParser
import zed.rainxch.kiristore.app.desktop.KeyboardNavigation
import zed.rainxch.kiristore.app.desktop.KeyboardNavigationEvent
import zed.rainxch.kiristore.app.navigation.AppNavigation
import zed.rainxch.kiristore.app.navigation.KiriStoreGraph
import zed.rainxch.kiristore.app.navigation.getCurrentScreen

@Composable
fun App(deepLinkUri: String? = null) {
    val viewModel: MainViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val currentScreen = navController.currentBackStackEntryAsState().value.getCurrentScreen()

    LaunchedEffect(deepLinkUri) {
        deepLinkUri?.let { uri ->
            when (val destination = DeepLinkParser.parse(uri)) {
                is DeepLinkDestination.Repository -> {
                    navController.navigate(
                        KiriStoreGraph.DetailsScreen(
                            owner = destination.owner,
                            repo = destination.repo,
                        ),
                    )
                }

                DeepLinkDestination.None -> {
                    // ignore unrecognized deep links
                }
            }
        }
    }

    ObserveAsEvents(KeyboardNavigation.events) { event ->
        when (event) {
            KeyboardNavigationEvent.OnCtrlFClick -> {
                if (currentScreen !is KiriStoreGraph.SearchScreen) {
                    navController.navigate(KiriStoreGraph.SearchScreen) {
                        popUpTo(KiriStoreGraph.HomeScreen) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }

    KiriStoreTheme(
        fontTheme = state.currentFontTheme,
        appTheme = state.currentColorTheme,
        isAmoledTheme = state.isAmoledTheme,
        isDarkTheme = state.isDarkTheme ?: isSystemInDarkTheme(),
    ) {
        ApplyAndroidSystemBars(state.isDarkTheme)

        if (state.showRateLimitDialog && state.rateLimitInfo != null) {
            RateLimitDialog(
                rateLimitInfo = state.rateLimitInfo!!,
                isAuthenticated = state.isLoggedIn,
                onDismiss = {
                    viewModel.onAction(MainAction.DismissRateLimitDialog)
                },
                onSignIn = {
                    viewModel.onAction(MainAction.DismissRateLimitDialog)

                    navController.navigate(KiriStoreGraph.AuthenticationScreen)
                },
            )
        }

        if (state.showSessionExpiredDialog) {
            SessionExpiredDialog(
                onDismiss = {
                    viewModel.onAction(MainAction.DismissSessionExpiredDialog)
                },
                onSignIn = {
                    viewModel.onAction(MainAction.DismissSessionExpiredDialog)
                    navController.navigate(KiriStoreGraph.AuthenticationScreen)
                },
            )
        }

        AppNavigation(
            navController = navController,
            isLiquidGlassEnabled = state.isLiquidGlassEnabled,
            isScrollbarEnabled = state.isScrollbarEnabled,
        )
    }
}

