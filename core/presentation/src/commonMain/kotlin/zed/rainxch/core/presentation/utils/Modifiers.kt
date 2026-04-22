package zed.rainxch.core.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquid

@Composable
fun Modifier.glassmorphism(
    liquidState: LiquidState? = null,
    enabled: Boolean = true,
    cornerRadius: Dp = 32.dp,
    alpha: Float = 0.15f,
): Modifier {
    if (!enabled || !isLiquidFrostAvailable()) {
        return this.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
            shape = RoundedCornerShape(cornerRadius)
        )
    }

    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val baseAlpha = if (isDark) alpha * 1.5f else alpha
    
    return this
        .clip(RoundedCornerShape(cornerRadius))
        .background(
            MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = baseAlpha)
        )
        .then(
            if (liquidState != null) {
                Modifier.liquid(liquidState) {
                    this.shape = RoundedCornerShape(cornerRadius)
                    this.frost = if (isDark) 16.dp else 12.dp
                    this.curve = 0.4f
                    this.refraction = 0.1f
                    this.dispersion = 0.2f
                    this.saturation = if (isDark) 0.5f else 0.7f
                    this.contrast = 1.6f
                }
            } else this
        )
        .border(
            width = 1.dp,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = if (isDark) 0.15f else 0.4f),
                    Color.White.copy(alpha = if (isDark) 0.05f else 0.1f),
                )
            ),
            shape = RoundedCornerShape(cornerRadius)
        )
}

@Composable
fun Modifier.premiumGradient(
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.tertiary
    )
): Modifier {
    return this.background(
        brush = Brush.linearGradient(colors = colors),
        alpha = 0.1f
    ).border(
        width = 1.dp,
        brush = Brush.linearGradient(colors = colors.map { it.copy(alpha = 0.5f) }),
        shape = RoundedCornerShape(32.dp)
    )
}
