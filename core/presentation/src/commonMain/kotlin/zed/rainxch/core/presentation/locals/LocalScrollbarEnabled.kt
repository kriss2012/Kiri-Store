package zed.rainxch.core.presentation.locals

import androidx.compose.runtime.compositionLocalOf

/**
 * CompositionLocal providing whether the scrollbar should be shown.
 * Defaults to false (no scrollbar).
 */
val LocalScrollbarEnabled = compositionLocalOf { false }
