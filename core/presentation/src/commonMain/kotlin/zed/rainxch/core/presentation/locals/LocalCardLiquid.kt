package zed.rainxch.core.presentation.locals

import androidx.compose.runtime.compositionLocalOf
import io.github.fletchmckee.liquid.LiquidState

val LocalCardLiquid = compositionLocalOf<LiquidState?> { null }
val LocalIsLiquidEnabled = compositionLocalOf { true }
