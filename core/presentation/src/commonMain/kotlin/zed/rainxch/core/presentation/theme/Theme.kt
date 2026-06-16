package zed.rainxch.core.presentation.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import zed.rainxch.core.domain.model.AppTheme
import zed.rainxch.core.domain.model.FontTheme
import zed.rainxch.core.presentation.utils.darkScheme
import zed.rainxch.core.presentation.utils.lightScheme

val oceanBlueLight =
    lightColorScheme(
        primary = primaryLight,
        onPrimary = onPrimaryLight,
        primaryContainer = primaryContainerLight,
        onPrimaryContainer = onPrimaryContainerLight,
        secondary = secondaryLight,
        onSecondary = onSecondaryLight,
        secondaryContainer = secondaryContainerLight,
        onSecondaryContainer = onSecondaryContainerLight,
        tertiary = tertiaryLight,
        onTertiary = onTertiaryLight,
        tertiaryContainer = tertiaryContainerLight,
        onTertiaryContainer = onTertiaryContainerLight,
        error = errorLight,
        onError = onErrorLight,
        errorContainer = errorContainerLight,
        onErrorContainer = onErrorContainerLight,
        background = backgroundLight,
        onBackground = onBackgroundLight,
        surface = surfaceLight,
        onSurface = onSurfaceLight,
        surfaceVariant = surfaceVariantLight,
        onSurfaceVariant = onSurfaceVariantLight,
        outline = outlineLight,
        outlineVariant = outlineVariantLight,
        scrim = scrimLight,
        inverseSurface = inverseSurfaceLight,
        inverseOnSurface = inverseOnSurfaceLight,
        inversePrimary = inversePrimaryLight,
        surfaceDim = surfaceDimLight,
        surfaceBright = surfaceBrightLight,
        surfaceContainerLowest = surfaceContainerLowestLight,
        surfaceContainerLow = surfaceContainerLowLight,
        surfaceContainer = surfaceContainerLight,
        surfaceContainerHigh = surfaceContainerHighLight,
        surfaceContainerHighest = surfaceContainerHighestLight,
    )

val oceanBlueDark =
    darkColorScheme(
        primary = primaryDark,
        onPrimary = onPrimaryDark,
        primaryContainer = primaryContainerDark,
        onPrimaryContainer = onPrimaryContainerDark,
        secondary = secondaryDark,
        onSecondary = onSecondaryDark,
        secondaryContainer = secondaryContainerDark,
        onSecondaryContainer = onSecondaryContainerDark,
        tertiary = tertiaryDark,
        onTertiary = onTertiaryDark,
        tertiaryContainer = tertiaryContainerDark,
        onTertiaryContainer = onTertiaryContainerDark,
        error = errorDark,
        onError = onErrorDark,
        errorContainer = errorContainerDark,
        onErrorContainer = onErrorContainerDark,
        background = backgroundDark,
        onBackground = onBackgroundDark,
        surface = surfaceDark,
        onSurface = onSurfaceDark,
        surfaceVariant = surfaceVariantDark,
        onSurfaceVariant = onSurfaceVariantDark,
        outline = outlineDark,
        outlineVariant = outlineVariantDark,
        scrim = scrimDark,
        inverseSurface = inverseSurfaceDark,
        inverseOnSurface = inverseOnSurfaceDark,
        inversePrimary = inversePrimaryDark,
        surfaceDim = surfaceDimDark,
        surfaceBright = surfaceBrightDark,
        surfaceContainerLowest = surfaceContainerLowestDark,
        surfaceContainerLow = surfaceContainerLowDark,
        surfaceContainer = surfaceContainerDark,
        surfaceContainerHigh = surfaceContainerHighDark,
        surfaceContainerHighest = surfaceContainerHighestDark,
    )

val deepPurpleLight =
    lightColorScheme(
        primary = Color(0xFF6750A4),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFFE9DDFF),
        onPrimaryContainer = Color(0xFF22005D),
        secondary = Color(0xFF625B71),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFFE8DEF8),
        onSecondaryContainer = Color(0xFF1E192B),
        tertiary = Color(0xFF7E5260),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFFFFD9E3),
        onTertiaryContainer = Color(0xFF31101D),
        error = Color(0xFFBA1A1A),
        onError = Color(0xFFFFFFFF),
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF410002),
        background = Color(0xFFFFFBFF),
        onBackground = Color(0xFF1C1B1E),
        surface = Color(0xFFFFFBFF),
        onSurface = Color(0xFF1C1B1E),
        surfaceVariant = Color(0xFFE7E0EB),
        onSurfaceVariant = Color(0xFF49454E),
        outline = Color(0xFF7A757F),
        outlineVariant = Color(0xFFCAC4CF),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFF313033),
        inverseOnSurface = Color(0xFFF4EFF4),
        inversePrimary = Color(0xFFCFBCFF),
        surfaceDim = Color(0xFFDED8DD),
        surfaceBright = Color(0xFFFFFBFF),
        surfaceContainerLowest = Color(0xFFFFFFFF),
        surfaceContainerLow = Color(0xFFF8F2F7),
        surfaceContainer = Color(0xFFF2ECF1),
        surfaceContainerHigh = Color(0xFFECE6EB),
        surfaceContainerHighest = Color(0xFFE6E1E6),
    )

val deepPurpleDark =
    darkColorScheme(
        primary = Color(0xFFCFBCFF),
        onPrimary = Color(0xFF381E72),
        primaryContainer = Color(0xFF4F378A),
        onPrimaryContainer = Color(0xFFE9DDFF),
        secondary = Color(0xFFCBC2DB),
        onSecondary = Color(0xFF332D41),
        secondaryContainer = Color(0xFF4A4458),
        onSecondaryContainer = Color(0xFFE8DEF8),
        tertiary = Color(0xFFEFB8C8),
        onTertiary = Color(0xFF4A2532),
        tertiaryContainer = Color(0xFF633B48),
        onTertiaryContainer = Color(0xFFFFD9E3),
        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),
        background = Color(0xFF141316),
        onBackground = Color(0xFFE6E1E6),
        surface = Color(0xFF141316),
        onSurface = Color(0xFFE6E1E6),
        surfaceVariant = Color(0xFF49454E),
        onSurfaceVariant = Color(0xFFCAC4CF),
        outline = Color(0xFF948F99),
        outlineVariant = Color(0xFF49454E),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFFE6E1E6),
        inverseOnSurface = Color(0xFF313033),
        inversePrimary = Color(0xFF6750A4),
        surfaceDim = Color(0xFF141316),
        surfaceBright = Color(0xFF3A383C),
        surfaceContainerLowest = Color(0xFF0F0E11),
        surfaceContainerLow = Color(0xFF1C1B1E),
        surfaceContainer = Color(0xFF201F22),
        surfaceContainerHigh = Color(0xFF2B292D),
        surfaceContainerHighest = Color(0xFF363438),
    )

// ============================================================================
// FOREST GREEN THEME (Trusted & Verified)
// ============================================================================
val forestGreenLight =
    lightColorScheme(
        primary = Color(0xFF356859),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFFB8EED9),
        onPrimaryContainer = Color(0xFF002019),
        secondary = Color(0xFF4C6359),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFFCEE9DB),
        onSecondaryContainer = Color(0xFF092018),
        tertiary = Color(0xFF3F6373),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFFC3E8FB),
        onTertiaryContainer = Color(0xFF001F29),
        error = Color(0xFFBA1A1A),
        onError = Color(0xFFFFFFFF),
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF410002),
        background = Color(0xFFF5FBF7),
        onBackground = Color(0xFF171D1A),
        surface = Color(0xFFF5FBF7),
        onSurface = Color(0xFF171D1A),
        surfaceVariant = Color(0xFFDBE5DD),
        onSurfaceVariant = Color(0xFF404943),
        outline = Color(0xFF707973),
        outlineVariant = Color(0xFFBFC9C1),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFF2C322F),
        inverseOnSurface = Color(0xFFEDF2ED),
        inversePrimary = Color(0xFF9CD1BD),
        surfaceDim = Color(0xFFD6DBD8),
        surfaceBright = Color(0xFFF5FBF7),
        surfaceContainerLowest = Color(0xFFFFFFFF),
        surfaceContainerLow = Color(0xFFF0F5F1),
        surfaceContainer = Color(0xFFEAEFEB),
        surfaceContainerHigh = Color(0xFFE4E9E6),
        surfaceContainerHighest = Color(0xFFDFE4E0),
    )

val forestGreenDark =
    darkColorScheme(
        primary = Color(0xFF9CD1BD),
        onPrimary = Color(0xFF00382B),
        primaryContainer = Color(0xFF1C4F41),
        onPrimaryContainer = Color(0xFFB8EED9),
        secondary = Color(0xFFB2CDBF),
        onSecondary = Color(0xFF1D352C),
        secondaryContainer = Color(0xFF344C42),
        onSecondaryContainer = Color(0xFFCEE9DB),
        tertiary = Color(0xFFA7CCDE),
        onTertiary = Color(0xFF0C3443),
        tertiaryContainer = Color(0xFF264B5B),
        onTertiaryContainer = Color(0xFFC3E8FB),
        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),
        background = Color(0xFF0F1512),
        onBackground = Color(0xFFDFE4E0),
        surface = Color(0xFF0F1512),
        onSurface = Color(0xFFDFE4E0),
        surfaceVariant = Color(0xFF404943),
        onSurfaceVariant = Color(0xFFBFC9C1),
        outline = Color(0xFF89938C),
        outlineVariant = Color(0xFF404943),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFFDFE4E0),
        inverseOnSurface = Color(0xFF2C322F),
        inversePrimary = Color(0xFF356859),
        surfaceDim = Color(0xFF0F1512),
        surfaceBright = Color(0xFF353B37),
        surfaceContainerLowest = Color(0xFF0A100D),
        surfaceContainerLow = Color(0xFF171D1A),
        surfaceContainer = Color(0xFF1B211E),
        surfaceContainerHigh = Color(0xFF262C28),
        surfaceContainerHighest = Color(0xFF313733),
    )

// ============================================================================
// SLATE GRAY THEME (Minimal Developer)
// ============================================================================
val slateGrayLight =
    lightColorScheme(
        primary = Color(0xFF535E6C),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFFD7E3F3),
        onPrimaryContainer = Color(0xFF101C27),
        secondary = Color(0xFF565E6B),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFFDAE2F1),
        onSecondaryContainer = Color(0xFF131C26),
        tertiary = Color(0xFF6E5676),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFFF7D9FF),
        onTertiaryContainer = Color(0xFF281430),
        error = Color(0xFFBA1A1A),
        onError = Color(0xFFFFFFFF),
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF410002),
        background = Color(0xFFF8F9FB),
        onBackground = Color(0xFF191C1E),
        surface = Color(0xFFF8F9FB),
        onSurface = Color(0xFF191C1E),
        surfaceVariant = Color(0xFFDFE2E9),
        onSurfaceVariant = Color(0xFF43474E),
        outline = Color(0xFF73777F),
        outlineVariant = Color(0xFFC3C6CD),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFF2E3133),
        inverseOnSurface = Color(0xFFF0F0F3),
        inversePrimary = Color(0xFFB4C7D9),
        surfaceDim = Color(0xFFD9D9DC),
        surfaceBright = Color(0xFFF8F9FB),
        surfaceContainerLowest = Color(0xFFFFFFFF),
        surfaceContainerLow = Color(0xFFF3F3F6),
        surfaceContainer = Color(0xFFEDEDF0),
        surfaceContainerHigh = Color(0xFFE7E8EA),
        surfaceContainerHighest = Color(0xFFE2E2E5),
    )

val slateGrayDark =
    darkColorScheme(
        primary = Color(0xFFB4C7D9),
        onPrimary = Color(0xFF1F2F3D),
        primaryContainer = Color(0xFF394654),
        onPrimaryContainer = Color(0xFFD7E3F3),
        secondary = Color(0xFFBEC6D5),
        onSecondary = Color(0xFF28323B),
        secondaryContainer = Color(0xFF3E4753),
        onSecondaryContainer = Color(0xFFDAE2F1),
        tertiary = Color(0xFFDABDE2),
        onTertiary = Color(0xFF3E2946),
        tertiaryContainer = Color(0xFF553F5D),
        onTertiaryContainer = Color(0xFFF7D9FF),
        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),
        background = Color(0xFF111416),
        onBackground = Color(0xFFE2E2E5),
        surface = Color(0xFF111416),
        onSurface = Color(0xFFE2E2E5),
        surfaceVariant = Color(0xFF43474E),
        onSurfaceVariant = Color(0xFFC3C6CD),
        outline = Color(0xFF8D9199),
        outlineVariant = Color(0xFF43474E),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFFE2E2E5),
        inverseOnSurface = Color(0xFF2E3133),
        inversePrimary = Color(0xFF535E6C),
        surfaceDim = Color(0xFF111416),
        surfaceBright = Color(0xFF37393B),
        surfaceContainerLowest = Color(0xFF0C0F11),
        surfaceContainerLow = Color(0xFF191C1E),
        surfaceContainer = Color(0xFF1D2022),
        surfaceContainerHigh = Color(0xFF282A2D),
        surfaceContainerHighest = Color(0xFF333538),
    )

// ============================================================================
// CYBERPUNK CODE QUEST THEME (Neon Cyan, Pink, Purple)
// ============================================================================
val cyberpunkLight =
    lightColorScheme(
        primary = Color(0xFF8A2BE2),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFFEBE1FF),
        onPrimaryContainer = Color(0xFF2B0066),
        secondary = Color(0xFFFF007F),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFFFFE5F0),
        onSecondaryContainer = Color(0xFF4A0020),
        tertiary = Color(0xFF009EAD),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFFD2F9FF),
        onTertiaryContainer = Color(0xFF002024),
        background = Color(0xFFFAF9FF),
        onBackground = Color(0xFF100E26),
        surface = Color(0xFFF4F1FD),
        onSurface = Color(0xFF100E26),
        surfaceVariant = Color(0xFFE6E1F4),
        onSurfaceVariant = Color(0xFF48445E),
        outline = Color(0xFF79748E),
        outlineVariant = Color(0xFFC9C4DC),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFF100E26),
        inverseOnSurface = Color(0xFFF4F1FD),
        inversePrimary = Color(0xFFD2BCFF),
        surfaceDim = Color(0xFFD9D5EB),
        surfaceBright = Color(0xFFFAF9FF),
        surfaceContainerLowest = Color(0xFFFFFFFF),
        surfaceContainerLow = Color(0xFFF3EDFD),
        surfaceContainer = Color(0xFFEDE7FA),
        surfaceContainerHigh = Color(0xFFE7E1F7),
        surfaceContainerHighest = Color(0xFFE1DBF4),
    )

val cyberpunkDark =
    darkColorScheme(
        primary = Color(0xFF00E5FF),
        onPrimary = Color(0xFF000000),
        primaryContainer = Color(0xFF0A0718),
        onPrimaryContainer = Color(0xFFF0EDFF),
        secondary = Color(0xFFFF007F),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFF1B153B),
        onSecondaryContainer = Color(0xFFF0EDFF),
        tertiary = Color(0xFF8A2BE2),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFF352975),
        onTertiaryContainer = Color(0xFFF0EDFF),
        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),
        background = Color(0xFF0A0718),
        onBackground = Color(0xFFF0EDFF),
        surface = Color(0xFF120E28),
        onSurface = Color(0xFFF0EDFF),
        surfaceVariant = Color(0xFF1B153B),
        onSurfaceVariant = Color(0xFF8C82B5),
        outline = Color(0xFF352975),
        outlineVariant = Color(0xFF120E28),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFFF0EDFF),
        inverseOnSurface = Color(0xFF0A0718),
        inversePrimary = Color(0xFF00E5FF),
        surfaceDim = Color(0xFF0A0718),
        surfaceBright = Color(0xFF1E193C),
        surfaceContainerLowest = Color(0xFF05030D),
        surfaceContainerLow = Color(0xFF120E28),
        surfaceContainer = Color(0xFF171231),
        surfaceContainerHigh = Color(0xFF201A40),
        surfaceContainerHighest = Color(0xFF2C2454),
    )

// ============================================================================
// AMBER ORANGE THEME (Energetic & Warm)
// ============================================================================
val amberOrangeLight =
    lightColorScheme(
        primary = Color(0xFF8B5000),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFFFFDCBE),
        onPrimaryContainer = Color(0xFF2D1600),
        secondary = Color(0xFF715A48),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFFFFDCBE),
        onSecondaryContainer = Color(0xFF28190A),
        tertiary = Color(0xFF54643D),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFFD7E9B8),
        onTertiaryContainer = Color(0xFF131F02),
        error = Color(0xFFBA1A1A),
        onError = Color(0xFFFFFFFF),
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF410002),
        background = Color(0xFFFFFBFF),
        onBackground = Color(0xFF201B16),
        surface = Color(0xFFFFFBFF),
        onSurface = Color(0xFF201B16),
        surfaceVariant = Color(0xFFF2DFD1),
        onSurfaceVariant = Color(0xFF51443A),
        outline = Color(0xFF837469),
        outlineVariant = Color(0xFFD5C3B6),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFF36302A),
        inverseOnSurface = Color(0xFFFAEFE7),
        inversePrimary = Color(0xFFFFB870),
        surfaceDim = Color(0xFFE4D9D1),
        surfaceBright = Color(0xFFFFFBFF),
        surfaceContainerLowest = Color(0xFFFFFFFF),
        surfaceContainerLow = Color(0xFFFEF3EB),
        surfaceContainer = Color(0xFFF8EDE5),
        surfaceContainerHigh = Color(0xFFF2E7DF),
        surfaceContainerHighest = Color(0xFFECE1DA),
    )

val amberOrangeDark =
    darkColorScheme(
        primary = Color(0xFFFFB870),
        onPrimary = Color(0xFF4B2800),
        primaryContainer = Color(0xFF6A3C00),
        onPrimaryContainer = Color(0xFFFFDCBE),
        secondary = Color(0xFFE2C1A3),
        onSecondary = Color(0xFF402D1D),
        secondaryContainer = Color(0xFF584332),
        onSecondaryContainer = Color(0xFFFFDCBE),
        tertiary = Color(0xFFBBCD9E),
        onTertiary = Color(0xFF273514),
        tertiaryContainer = Color(0xFF3D4C28),
        onTertiaryContainer = Color(0xFFD7E9B8),
        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),
        background = Color(0xFF18130E),
        onBackground = Color(0xFFECE1DA),
        surface = Color(0xFF18130E),
        onSurface = Color(0xFFECE1DA),
        surfaceVariant = Color(0xFF51443A),
        onSurfaceVariant = Color(0xFFD5C3B6),
        outline = Color(0xFF9D8E82),
        outlineVariant = Color(0xFF51443A),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFFECE1DA),
        inverseOnSurface = Color(0xFF36302A),
        inversePrimary = Color(0xFF8B5000),
        surfaceDim = Color(0xFF18130E),
        surfaceBright = Color(0xFF3F3933),
        surfaceContainerLowest = Color(0xFF120E09),
        surfaceContainerLow = Color(0xFF201B16),
        surfaceContainer = Color(0xFF241F1A),
        surfaceContainerHigh = Color(0xFF2F2A24),
        surfaceContainerHighest = Color(0xFF3A342E),
    )

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun KiriStoreTheme(
    isDarkTheme: Boolean = false,
    appTheme: AppTheme = AppTheme.OCEAN,
    fontTheme: FontTheme = FontTheme.CUSTOM,
    isAmoledTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val baseColorScheme =
        when {
            appTheme == AppTheme.DYNAMIC -> {
                getDynamicColorScheme(isDarkTheme) ?: run {
                    if (isDarkTheme) AppTheme.OCEAN.darkScheme else AppTheme.OCEAN.lightScheme
                }
            }

            isDarkTheme -> {
                appTheme.darkScheme!!
            }

            else -> {
                appTheme.lightScheme!!
            }
        }

    val colorScheme =
        if (isDarkTheme && isAmoledTheme) {
            baseColorScheme?.toAmoled()
        } else {
            baseColorScheme
        }

    val animatedColorScheme = colorScheme?.let { animateColorScheme(it) } ?: colorScheme

    MaterialExpressiveTheme(
        colorScheme = animatedColorScheme,
        typography = getAppTypography(fontTheme),
        motionScheme = MotionScheme.expressive(),
        shapes = MaterialTheme.shapes,
        content = content,
    )
}

@Composable
fun animateColorScheme(targetScheme: ColorScheme): ColorScheme {
    val animSpec = tween<Color>(durationMillis = 500)
    return ColorScheme(
        primary = animateColorAsState(targetScheme.primary, animSpec).value,
        onPrimary = animateColorAsState(targetScheme.onPrimary, animSpec).value,
        primaryContainer = animateColorAsState(targetScheme.primaryContainer, animSpec).value,
        onPrimaryContainer = animateColorAsState(targetScheme.onPrimaryContainer, animSpec).value,
        inversePrimary = animateColorAsState(targetScheme.inversePrimary, animSpec).value,
        secondary = animateColorAsState(targetScheme.secondary, animSpec).value,
        onSecondary = animateColorAsState(targetScheme.onSecondary, animSpec).value,
        secondaryContainer = animateColorAsState(targetScheme.secondaryContainer, animSpec).value,
        onSecondaryContainer = animateColorAsState(targetScheme.onSecondaryContainer, animSpec).value,
        tertiary = animateColorAsState(targetScheme.tertiary, animSpec).value,
        onTertiary = animateColorAsState(targetScheme.onTertiary, animSpec).value,
        tertiaryContainer = animateColorAsState(targetScheme.tertiaryContainer, animSpec).value,
        onTertiaryContainer = animateColorAsState(targetScheme.onTertiaryContainer, animSpec).value,
        background = animateColorAsState(targetScheme.background, animSpec).value,
        onBackground = animateColorAsState(targetScheme.onBackground, animSpec).value,
        surface = animateColorAsState(targetScheme.surface, animSpec).value,
        onSurface = animateColorAsState(targetScheme.onSurface, animSpec).value,
        surfaceVariant = animateColorAsState(targetScheme.surfaceVariant, animSpec).value,
        onSurfaceVariant = animateColorAsState(targetScheme.onSurfaceVariant, animSpec).value,
        surfaceTint = animateColorAsState(targetScheme.surfaceTint, animSpec).value,
        inverseSurface = animateColorAsState(targetScheme.inverseSurface, animSpec).value,
        inverseOnSurface = animateColorAsState(targetScheme.inverseOnSurface, animSpec).value,
        error = animateColorAsState(targetScheme.error, animSpec).value,
        onError = animateColorAsState(targetScheme.onError, animSpec).value,
        errorContainer = animateColorAsState(targetScheme.errorContainer, animSpec).value,
        onErrorContainer = animateColorAsState(targetScheme.onErrorContainer, animSpec).value,
        outline = animateColorAsState(targetScheme.outline, animSpec).value,
        outlineVariant = animateColorAsState(targetScheme.outlineVariant, animSpec).value,
        scrim = animateColorAsState(targetScheme.scrim, animSpec).value,
        surfaceDim = animateColorAsState(targetScheme.surfaceDim, animSpec).value,
        surfaceBright = animateColorAsState(targetScheme.surfaceBright, animSpec).value,
        surfaceContainerLowest = animateColorAsState(targetScheme.surfaceContainerLowest, animSpec).value,
        surfaceContainerLow = animateColorAsState(targetScheme.surfaceContainerLow, animSpec).value,
        surfaceContainer = animateColorAsState(targetScheme.surfaceContainer, animSpec).value,
        surfaceContainerHigh = animateColorAsState(targetScheme.surfaceContainerHigh, animSpec).value,
        surfaceContainerHighest = animateColorAsState(targetScheme.surfaceContainerHighest, animSpec).value,
    )
}

expect fun isDynamicColorAvailable(): Boolean

@Composable
expect fun getDynamicColorScheme(darkTheme: Boolean): ColorScheme?

