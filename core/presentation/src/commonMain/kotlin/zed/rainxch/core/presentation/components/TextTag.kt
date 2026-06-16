package zed.rainxch.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextTag(
    text: String,
    modifier: Modifier = Modifier,
    icon: String? = null,
    glowColor: Color? = null,
    containerColor: Color? = null,
) {
    val defaultGlow = glowColor ?: MaterialTheme.colorScheme.primary
    val defaultContainer = containerColor ?: MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    
    // Custom neon glow shadow using standard cross-platform shadow modifiers
    val glowModifier = if (glowColor != null) {
        Modifier.shadow(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            clip = false,
            ambientColor = defaultGlow.copy(alpha = 0.5f),
            spotColor = defaultGlow
        )
    } else Modifier

    Row(
        modifier = modifier
            .then(glowModifier)
            .clip(RoundedCornerShape(12.dp))
            .background(defaultContainer)
            .border(
                width = 1.dp,
                color = (glowColor ?: MaterialTheme.colorScheme.outline).copy(alpha = 0.35f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!icon.isNullOrEmpty()) {
            Text(
                text = icon,
                fontSize = 13.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                color = if (glowColor != null) glowColor else MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}
