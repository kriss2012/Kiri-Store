package zed.rainxch.search.presentation.utils

import org.jetbrains.compose.resources.StringResource
import zed.rainxch.kiristore.core.presentation.res.*
import zed.rainxch.search.presentation.model.SortOrderUi
import zed.rainxch.search.presentation.model.SortOrderUi.*

fun SortOrderUi.label(): StringResource =
    when (this) {
        Descending -> Res.string.sort_order_descending
        Ascending -> Res.string.sort_order_ascending
    }

