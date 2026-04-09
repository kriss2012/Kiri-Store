package zed.rainxch.core.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import zed.rainxch.core.data.local.db.entities.SeenRepoEntity

@Dao
interface SeenRepoDao {
    @Query("SELECT repoId FROM seen_repos")
    fun getAllSeenRepoIds(): Flow<List<Long>>

    @Query("SELECT * FROM seen_repos ORDER BY seenAt DESC")
    fun getAllSeenRepos(): Flow<List<SeenRepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SeenRepoEntity)

    @Query("DELETE FROM seen_repos WHERE repoId = :repoId")
    suspend fun deleteById(repoId: Long)

    @Query("DELETE FROM seen_repos")
    suspend fun clearAll()
}
