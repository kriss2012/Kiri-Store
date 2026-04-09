package zed.rainxch.recentlyviewed.presentation.model

data class RecentlyViewedRepo(
    val repoId: Long,
    val repoName: String,
    val repoOwner: String,
    val repoOwnerAvatarUrl: String,
    val repoDescription: String?,
    val primaryLanguage: String?,
    val repoUrl: String,
    val viewedAtFormatted: String,
)
