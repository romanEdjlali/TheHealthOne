package com.roman.thehealthone.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roman.thehealthone.data.model.ConflictLog
import com.roman.thehealthone.data.model.ConflictStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ConflictDao {

    @Query("SELECT * FROM conflict_log ORDER BY startTime DESC")
    fun observeAllConflicts(): Flow<List<ConflictLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertConflicts(items: List<ConflictLog>)

    @Query("UPDATE conflict_log SET resolvedWithId = :winnerId, status = :status WHERE conflictId = :id")
    suspend fun resolveConflict(
        id: Long, winnerId: String, status: ConflictStatus = ConflictStatus.RESOLVED
    )

    @Query("DELETE FROM conflict_log")
    suspend fun clearConflicts()
}