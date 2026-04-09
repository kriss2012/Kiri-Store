package zed.rainxch.search.presentation.mappers

import zed.rainxch.core.domain.model.DiscoveryPlatform
import zed.rainxch.core.domain.model.DiscoveryPlatform.*
import zed.rainxch.search.presentation.model.SearchPlatformUi

fun SearchPlatformUi.toDomain(): DiscoveryPlatform =
    when (this) {
        SearchPlatformUi.All -> All
        SearchPlatformUi.Android -> Android
        SearchPlatformUi.Windows -> Windows
        SearchPlatformUi.Macos -> Macos
        SearchPlatformUi.Linux -> Linux
    }
