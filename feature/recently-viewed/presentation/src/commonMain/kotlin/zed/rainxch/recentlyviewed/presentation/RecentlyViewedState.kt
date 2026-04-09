package zed.rainxch.recentlyviewed.presentation

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import zed.rainxch.recentlyviewed.presentation.model.RecentlyViewedRepo

data class RecentlyViewedState(
    val repositories: ImmutableList<RecentlyViewedRepo> = persistentListOf(),
    val isLoading: Boolean = false,
)
