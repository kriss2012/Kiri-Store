package zed.rainxch.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.LiquidState
import zed.rainxch.core.presentation.utils.glassmorphism

@Composable
fun ExpressiveCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    liquidState: LiquidState? = null,
    isLiquidEnabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    if (onClick != null) {
        ElevatedCard(
            modifier = modifier
                .fillMaxWidth()
                .glassmorphism(
                    liquidState = liquidState,
                    enabled = isLiquidEnabled,
                    cornerRadius = 32.dp
                ),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.Transparent, // Managed by glassmorphism
            ),
            onClick = onClick,
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
            content = { Box { content() } },
        )
    } else {
        ElevatedCard(
            modifier = modifier
                .fillMaxWidth()
                .glassmorphism(
                    liquidState = liquidState,
                    enabled = isLiquidEnabled,
                    cornerRadius = 32.dp
                ),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
            content = { Box { content() } },
        )
    }
}
