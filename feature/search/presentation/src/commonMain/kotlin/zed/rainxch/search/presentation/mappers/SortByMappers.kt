package zed.rainxch.search.presentation.mappers

import zed.rainxch.domain.model.SortBy
import zed.rainxch.domain.model.SortBy.*
import zed.rainxch.search.presentation.model.SortByUi

fun SortByUi.toDomain(): SortBy {
    return when (this) {
        SortByUi.MostStars -> MostStars
        SortByUi.MostForks -> MostForks
        SortByUi.BestMatch -> BestMatch
    }
}
