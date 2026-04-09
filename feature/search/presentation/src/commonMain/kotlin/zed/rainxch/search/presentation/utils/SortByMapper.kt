package zed.rainxch.search.presentation.utils

import org.jetbrains.compose.resources.StringResource
import zed.rainxch.kiristore.core.presentation.res.*
import zed.rainxch.search.presentation.model.SortByUi
import zed.rainxch.search.presentation.model.SortByUi.*

fun SortByUi.label(): StringResource =
    when (this) {
        MostStars -> Res.string.sort_most_stars
        MostForks -> Res.string.sort_most_forks
        BestMatch -> Res.string.sort_best_match
    }

