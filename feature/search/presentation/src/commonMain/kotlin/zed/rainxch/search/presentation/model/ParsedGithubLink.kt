package zed.rainxch.search.presentation.model

data class ParsedGithubLink(
    val owner: String,
    val repo: String,
    val fullUrl: String,
)
