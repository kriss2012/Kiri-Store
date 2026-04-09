package zed.rainxch.search.presentation.utils

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import zed.rainxch.search.presentation.model.ParsedGithubLink

private val GITHUB_URL_REGEX =
    Regex(
        """(?<![A-Za-z0-9.-])(?:https?://)?(?:www\.)?github\.com/([a-zA-Z0-9\-_.]+)/([a-zA-Z0-9\-_.]+)""",
    )

fun parseGithubUrls(text: String): ImmutableList<ParsedGithubLink> =
    GITHUB_URL_REGEX
        .findAll(text)
        .map { match ->
            ParsedGithubLink(
                owner = match.groupValues[1],
                repo = match.groupValues[2].removeSuffix(".git"),
                fullUrl = "https://github.com/${match.groupValues[1]}/${match.groupValues[2].removeSuffix(".git")}",
            )
        }.distinctBy { "${it.owner}/${it.repo}" }
        .toImmutableList()

fun isEntirelyGithubUrls(text: String): Boolean {
    val stripped =
        text
            .replace(GITHUB_URL_REGEX, "")
            .replace(Regex("""[\s,;]+"""), "")
    return stripped.isEmpty() && parseGithubUrls(text).isNotEmpty()
}
