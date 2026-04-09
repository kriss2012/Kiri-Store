package zed.rainxch.core.domain.repository

import kotlinx.coroutines.flow.Flow
import zed.rainxch.core.domain.model.GithubRepoSummary
import zed.rainxch.core.domain.model.SeenRepo

interface SeenReposRepository {
    fun getAllSeenRepoIds(): Flow<Set<Long>>

    fun getAllSeenRepos(): Flow<List<SeenRepo>>

    suspend fun markAsSeen(repo: GithubRepoSummary)

    suspend fun removeFromHistory(repoId: Long)

    suspend fun clearAll()
}
