package zed.rainxch.kiristore.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import zed.rainxch.core.domain.getPlatform
import zed.rainxch.core.domain.model.Platform
import zed.rainxch.kiristore.core.presentation.res.*

data class BottomNavigationItem(
    val titleRes: StringResource,
    val iconOutlined: ImageVector,
    val iconFilled: ImageVector,
    val screen: KiriStoreGraph,
)

object BottomNavigationUtils {
    fun items(): List<BottomNavigationItem> =
        listOf(
            BottomNavigationItem(
                titleRes = Res.string.bottom_nav_home_title,
                iconOutlined = Icons.Outlined.Home,
                iconFilled = Icons.Filled.Home,
                screen = KiriStoreGraph.HomeScreen,
            ),
            BottomNavigationItem(
                titleRes = Res.string.bottom_nav_search_title,
                iconOutlined = Icons.Outlined.Search,
                iconFilled = Icons.Filled.Search,
                screen = KiriStoreGraph.SearchScreen,
            ),
            BottomNavigationItem(
                titleRes = Res.string.bottom_nav_apps_title,
                iconOutlined = Icons.Outlined.Apps,
                iconFilled = Icons.Filled.Apps,
                screen = KiriStoreGraph.AppsScreen,
            ),
            BottomNavigationItem(
                titleRes = Res.string.bottom_nav_profile_title,
                iconOutlined = Icons.Outlined.Person2,
                iconFilled = Icons.Filled.Person2,
                screen = KiriStoreGraph.ProfileScreen,
            ),
            BottomNavigationItem(
                titleRes = Res.string.bottom_nav_profile_tweaks,
                iconOutlined = Icons.Outlined.Settings,
                iconFilled = Icons.Filled.Settings,
                screen = KiriStoreGraph.TweaksScreen,
            ),
        )

    fun allowedScreens(): List<BottomNavigationItem> =
        items()
            .filterNot {
                getPlatform() != Platform.ANDROID &&
                    it.screen == KiriStoreGraph.AppsScreen
            }
}

