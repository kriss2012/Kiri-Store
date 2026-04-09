package zed.rainxch.details.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.GTranslate
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import zed.rainxch.details.presentation.model.TranslationState
import zed.rainxch.kiristore.core.presentation.res.*

@Composable
fun TranslationControls(
    translationState: TranslationState,
    onTranslateClick: () -> Unit,
    onLanguagePickerClick: () -> Unit,
    onToggleTranslation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = translationState.controlState,
        modifier = modifier,
        transitionSpec = {
            (fadeIn() + slideInHorizontally { it / 3 }) togetherWith
                (fadeOut() + slideOutHorizontally { -it / 3 })
        },
        label = "translation_controls",
    ) { state ->
        when (state) {
            TranslationControlState.IDLE -> {
                IdleControls(
                    onTranslateClick = onTranslateClick,
                    onLanguagePickerClick = onLanguagePickerClick,
                )
            }

            TranslationControlState.TRANSLATING -> {
                TranslatingIndicator()
            }

            TranslationControlState.SHOWING_TRANSLATION -> {
                TranslatedControls(
                    displayName = translationState.targetLanguageDisplayName,
                    isShowingTranslation = true,
                    onToggle = onToggleTranslation,
                    onLanguagePickerClick = onLanguagePickerClick,
                )
            }

            TranslationControlState.SHOWING_ORIGINAL -> {
                TranslatedControls(
                    displayName = translationState.targetLanguageDisplayName,
                    isShowingTranslation = false,
                    onToggle = onToggleTranslation,
                    onLanguagePickerClick = onLanguagePickerClick,
                )
            }

            TranslationControlState.ERROR -> {
                ErrorControls(
                    onRetry = onTranslateClick,
                    onLanguagePickerClick = onLanguagePickerClick,
                )
            }
        }
    }
}

@Composable
private fun IdleControls(
    onTranslateClick: () -> Unit,
    onLanguagePickerClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .clickable(onClick = onTranslateClick)
                .padding(start = 10.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.GTranslate,
            contentDescription = stringResource(Res.string.translate),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp),
        )
        Spacer(Modifier.width(5.dp))
        Text(
            text = stringResource(Res.string.translate),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        LanguageDropdownButton(onLanguagePickerClick)
    }
}

@Composable
private fun TranslatingIndicator() {
    Row(
        modifier =
            Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(14.dp),
            strokeWidth = 2.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Text(
            text = stringResource(Res.string.translating),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Composable
private fun TranslatedControls(
    displayName: String?,
    isShowingTranslation: Boolean,
    onToggle: () -> Unit,
    onLanguagePickerClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        if (isShowingTranslation) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceContainerHigh
                        },
                    ).clickable(onClick = onToggle)
                    .padding(start = 10.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(
                visible = isShowingTranslation,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut(),
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.GTranslate,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                }
            }

            Text(
                text =
                    if (isShowingTranslation) {
                        stringResource(Res.string.show_original)
                    } else {
                        displayName ?: stringResource(Res.string.translate)
                    },
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color =
                    if (isShowingTranslation) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
            )

            LanguageDropdownButton(
                onClick = onLanguagePickerClick,
                tint =
                    if (isShowingTranslation) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
            )
        }
    }
}

@Composable
private fun ErrorControls(
    onRetry: () -> Unit,
    onLanguagePickerClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.errorContainer)
                .clickable(onClick = onRetry)
                .padding(start = 10.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = stringResource(Res.string.translation_error_retry),
            tint = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.size(14.dp),
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = stringResource(Res.string.translation_error_retry),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onErrorContainer,
        )
        LanguageDropdownButton(
            onClick = onLanguagePickerClick,
            tint = MaterialTheme.colorScheme.onErrorContainer,
        )
    }
}

@Composable
private fun LanguageDropdownButton(
    onClick: () -> Unit,
    tint: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Icon(
        imageVector = Icons.Default.ArrowDropDown,
        contentDescription = stringResource(Res.string.change_language),
        tint = tint,
        modifier =
            Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = onClick)
                .padding(2.dp),
    )
}

private enum class TranslationControlState {
    IDLE,
    TRANSLATING,
    SHOWING_TRANSLATION,
    SHOWING_ORIGINAL,
    ERROR,
}

private val TranslationState.controlState: TranslationControlState
    get() =
        when {
            isTranslating -> TranslationControlState.TRANSLATING
            error != null && translatedText == null -> TranslationControlState.ERROR
            isShowingTranslation && translatedText != null -> TranslationControlState.SHOWING_TRANSLATION
            !isShowingTranslation && translatedText != null -> TranslationControlState.SHOWING_ORIGINAL
            else -> TranslationControlState.IDLE
        }

