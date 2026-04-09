package zed.rainxch.core.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val primaryLight = Color(0xFF0061A4)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFD1E4FF)
val onPrimaryContainerLight = Color(0xFF001D36)
val secondaryLight = Color(0xFF535F70)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFD7E3F7)
val onSecondaryContainerLight = Color(0xFF101C2B)
val tertiaryLight = Color(0xFF6B5778)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFF2DAFF)
val onTertiaryContainerLight = Color(0xFF251431)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF410002)
val backgroundLight = Color(0xFFF8F9FF)
val onBackgroundLight = Color(0xFF191C20)
val surfaceLight = Color(0xFFF8F9FF)
val onSurfaceLight = Color(0xFF191C20)
val surfaceVariantLight = Color(0xFFDFE2EB)
val onSurfaceVariantLight = Color(0xFF43474E)
val outlineLight = Color(0xFF73777F)
val outlineVariantLight = Color(0xFFC3C7CF)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF2E3135)
val inverseOnSurfaceLight = Color(0xFFF0F0F4)
val inversePrimaryLight = Color(0xFF9ECAFF)
val surfaceDimLight = Color(0xFFD9D9E0)
val surfaceBrightLight = Color(0xFFF8F9FF)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF2F3F9)
val surfaceContainerLight = Color(0xFFECEEF4)
val surfaceContainerHighLight = Color(0xFFE7E8EE)
val surfaceContainerHighestLight = Color(0xFFE1E2E8)

val primaryDark = Color(0xFF9ECAFF)
val onPrimaryDark = Color(0xFF003258)
val primaryContainerDark = Color(0xFF00497D)
val onPrimaryContainerDark = Color(0xFFD1E4FF)
val secondaryDark = Color(0xFFBBC7DB)
val onSecondaryDark = Color(0xFF253140)
val secondaryContainerDark = Color(0xFF3B4858)
val onSecondaryContainerDark = Color(0xFFD7E3F7)
val tertiaryDark = Color(0xFFD7BEE4)
val onTertiaryDark = Color(0xFF3B2948)
val tertiaryContainerDark = Color(0xFF523F5F)
val onTertiaryContainerDark = Color(0xFFF2DAFF)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF111318)
val onBackgroundDark = Color(0xFFE1E2E8)
val surfaceDark = Color(0xFF111318)
val onSurfaceDark = Color(0xFFE1E2E8)
val surfaceVariantDark = Color(0xFF43474E)
val onSurfaceVariantDark = Color(0xFFC3C7CF)
val outlineDark = Color(0xFF8D9199)
val outlineVariantDark = Color(0xFF43474E)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFE1E2E8)
val inverseOnSurfaceDark = Color(0xFF2E3135)
val inversePrimaryDark = Color(0xFF0061A4)
val surfaceDimDark = Color(0xFF111318)
val surfaceBrightDark = Color(0xFF37393E)
val surfaceContainerLowestDark = Color(0xFF0C0E13)
val surfaceContainerLowDark = Color(0xFF191C20)
val surfaceContainerDark = Color(0xFF1D2024)
val surfaceContainerHighDark = Color(0xFF282A2F)
val surfaceContainerHighestDark = Color(0xFF333539)

fun ColorScheme.toAmoled(): ColorScheme =
    this.copy(
        background = Color.Black,
        surface = Color.Black,
        surfaceContainer = Color(0xFF0A0A0A),
        surfaceContainerLow = Color(0xFF050505),
        surfaceContainerLowest = Color.Black,
        surfaceContainerHigh = Color(0xFF121212),
        surfaceContainerHighest = Color(0xFF1A1A1A),
        surfaceDim = Color(0xFF0D0D0D),
        surfaceBright = Color(0xFF1F1F1F),
        surfaceVariant = Color(0xFF121212),
    )
