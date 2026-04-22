package zed.rainxch.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.CallSplit
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import zed.rainxch.core.domain.model.DiscoveryPlatform
import zed.rainxch.core.presentation.model.*
import zed.rainxch.core.presentation.locals.LocalCardLiquid
import zed.rainxch.core.presentation.locals.LocalIsLiquidEnabled
import zed.rainxch.core.presentation.theme.KiriStoreTheme
import zed.rainxch.core.presentation.utils.formatReleasedAt
import zed.rainxch.core.presentation.utils.hasWeekNotPassed
import zed.rainxch.core.presentation.utils.premiumGradient
import zed.rainxch.core.presentation.utils.toIcons
import zed.rainxch.kiristore.core.presentation.res.Res
import zed.rainxch.kiristore.core.presentation.res.forked_repository
import zed.rainxch.kiristore.core.presentation.res.home_view_details
import zed.rainxch.kiristore.core.presentation.res.installed
import zed.rainxch.kiristore.core.presentation.res.open_in_browser
import zed.rainxch.kiristore.core.presentation.res.seen_badge
import zed.rainxch.kiristore.core.presentation.res.share_repository
import zed.rainxch.kiristore.core.presentation.res.update_available

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalLayoutApi::class)
@Composable
fun RepositoryCard(
    discoveryRepositoryUi: DiscoveryRepositoryUi,
    onClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeveloperClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val liquidState = LocalCardLiquid.current
    val isLiquidEnabled = LocalIsLiquidEnabled.current
    val uriHandler = LocalUriHandler.current

    ExpressiveCard(
        onClick = onClick,
        liquidState = liquidState,
        isLiquidEnabled = isLiquidEnabled,
        modifier = modifier,
    ) {
        Box {
            if (discoveryRepositoryUi.isFavourite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                    modifier =
                        Modifier
                            .size(120.dp)
                            .align(Alignment.BottomStart)
                            .offset(x = (-32).dp, y = 32.dp),
                )
            }

            if (discoveryRepositoryUi.isStarred) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f),
                    modifier =
                        Modifier
                            .size(120.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 32.dp, y = (-32).dp),
                )
            }

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .clip(CircleShape)
                                .clickable(onClick = {
                                    onDeveloperClick(discoveryRepositoryUi.repository.owner.login)
                                })
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        KiriStoreImage(
                            imageModel = { discoveryRepositoryUi.repository.owner.avatarUrl },
                            modifier =
                                Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                        )

                        Text(
                            text = discoveryRepositoryUi.repository.owner.login,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.outline,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Text(
                        text = "/",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.weight(1f),
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = discoveryRepositoryUi.repository.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false),
                    )

                    if (discoveryRepositoryUi.repository.isFork) {
                        ForkBadge()
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                discoveryRepositoryUi.repository.description?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge,
                        softWrap = true,
                    )
                }

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD700), // Gold
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = discoveryRepositoryUi.repository.stargazersCount.toString(),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.CallSplit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = discoveryRepositoryUi.repository.forksCount.toString(),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    discoveryRepositoryUi.repository.language?.let {
                        Text(
                            text = "• $it",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

                if (discoveryRepositoryUi.isInstalled || discoveryRepositoryUi.isSeen) {
                    Spacer(Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (discoveryRepositoryUi.isInstalled) {
                            InstallStatusBadge(
                                isUpdateAvailable = discoveryRepositoryUi.isUpdateAvailable,
                            )
                        }

                        if (discoveryRepositoryUi.isSeen) {
                            SeenBadge()
                        }
                    }
                }

                if (discoveryRepositoryUi.repository.availablePlatforms.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        discoveryRepositoryUi.repository.availablePlatforms.forEach { platform ->
                            PlatformChip(platform = platform)
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                val releasedAtText =
                    buildAnnotatedString {
                        if (hasWeekNotPassed(discoveryRepositoryUi.repository.updatedAt)) {
                            append("🔥 ")
                        }
                        append(formatReleasedAt(discoveryRepositoryUi.repository.updatedAt))
                    }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .then(
                            if (hasWeekNotPassed(discoveryRepositoryUi.repository.updatedAt)) {
                                Modifier.premiumGradient(
                                    colors = listOf(
                                        Color(0xFFFF8C00), // DarkOrange
                                        Color(0xFFFF4500)  // OrangeRed
                                    )
                                )
                            } else Modifier
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = releasedAtText,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (hasWeekNotPassed(discoveryRepositoryUi.repository.updatedAt)) {
                            Color.White
                        } else {
                            MaterialTheme.colorScheme.outline
                        },
                        fontWeight = if (hasWeekNotPassed(discoveryRepositoryUi.repository.updatedAt)) {
                            FontWeight.Bold
                        } else FontWeight.Medium,
                        maxLines = 1,
                    )
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    KiriStoreButton(
                        text = stringResource(Res.string.home_view_details),
                        onClick = onClick,
                        modifier = Modifier.weight(1f),
                    )

                    IconButton(
                        onClick = onShareClick,
                        colors =
                            IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            ),
                        shapes = IconButtonDefaults.shapes(),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = stringResource(Res.string.share_repository),
                        )
                    }

                    IconButton(
                        onClick = {
                            uriHandler.openUri(discoveryRepositoryUi.repository.htmlUrl)
                        },
                        colors =
                            IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            ),
                        shapes = IconButtonDefaults.shapes(),
                    ) {
                        Icon(
                            imageVector = Icons.Default.OpenInBrowser,
                            contentDescription = stringResource(Res.string.open_in_browser),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlatformChip(
    platform: DiscoveryPlatform,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
    ) {
        FlowRow(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        ) {
            platform.toIcons().forEach { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }

            Text(
                text = platform.name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            )
        }
    }
}

@Composable
fun ForkBadge(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.CallSplit,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Text(
                text = stringResource(Res.string.forked_repository),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
fun InstallStatusBadge(
    isUpdateAvailable: Boolean,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (isUpdateAvailable) {
            MaterialTheme.colorScheme.tertiaryContainer
        } else {
            MaterialTheme.colorScheme.primaryContainer
        }

    val textColor =
        if (isUpdateAvailable) {
            MaterialTheme.colorScheme.onTertiaryContainer
        } else {
            MaterialTheme.colorScheme.onPrimaryContainer
        }

    val icon =
        if (isUpdateAvailable) {
            Icons.Default.Update
        } else {
            Icons.Default.CheckCircle
        }

    val text =
        if (isUpdateAvailable) {
            stringResource(Res.string.update_available)
        } else {
            stringResource(Res.string.installed)
        }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = textColor,
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = textColor,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
fun SeenBadge(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Visibility,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = stringResource(Res.string.seen_badge),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Preview
@Composable
fun RepositoryCardPreview() {
    KiriStoreTheme {
        RepositoryCard(
            discoveryRepositoryUi =
                DiscoveryRepositoryUi(
                    repository =
                        GithubRepoSummaryUi(
                            id = 0L,
                            name = "Hello",
                            fullName = "JIFEOJEF",
                            owner =
                                GithubUserUi(
                                    id = 0L,
                                    login = "Skydoves",
                                    avatarUrl = "ewfew",
                                    htmlUrl = "grgrre",
                                ),
                            description = "Hello wolrd Hello wolrd Hello wolrd Hello wolrd Hello wolrd",
                            htmlUrl = "",
                            stargazersCount = 20,
                            forksCount = 4,
                            language = "Kotlin",
                            topics = null,
                            releasesUrl = "",
                            updatedAt = "2025-12-01T12:00:00Z",
                            defaultBranch = "",
                        ),
                    isUpdateAvailable = true,
                    isFavourite = true,
                    isInstalled = true,
                    isStarred = false,
                ),
            onClick = { },
            onShareClick = { },
            onDeveloperClick = { },
        )
    }
}

