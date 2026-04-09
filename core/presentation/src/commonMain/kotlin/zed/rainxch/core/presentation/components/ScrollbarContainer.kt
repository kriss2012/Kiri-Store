package zed.rainxch.core.presentation.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Wraps content with a platform-appropriate scrollbar.
 * On Desktop (JVM), adds a VerticalScrollbar when [enabled] is true.
 * On Android, renders only the [content] (no scrollbar).
 */
@Composable
expect fun ScrollbarContainer(
    listState: LazyListState,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
)

/**
 * Overload for [LazyStaggeredGridState].
 */
@Composable
expect fun ScrollbarContainer(
    gridState: LazyStaggeredGridState,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
)
