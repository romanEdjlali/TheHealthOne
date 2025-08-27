package com.roman.thehealthone.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roman.thehealthone.data.model.SleepLog
import androidx.room.*


// SleepDao.kt
@Dao
interface SleepDao {

    @Query("SELECT * FROM sleep_log WHERE isDeleted = 0 ORDER BY startTime DESC")
    suspend fun getAll(): List<SleepLog>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: SleepLog)

    @Update
    suspend fun update(log: SleepLog)

    @Delete
    suspend fun delete(log: SleepLog)

    @Query("UPDATE sleep_log SET isDeleted = 1 WHERE id = :id")
    suspend fun softDelete(id: String)
}



/*@Dao
interface SleepDao {
    @Query("SELECT * FROM sleep_log")
    suspend fun getAll(): List<SleepLog>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: SleepLog)

    @Query("SELECT * FROM sleep_log WHERE isDeleted = 0 ORDER BY startTime DESC")
    fun observeAllSleep(): Flow<List<SleepLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSleep(items: List<SleepLog>)

    @Query("DELETE FROM sleep_log WHERE id IN (:ids)")
    suspend fun deleteSleepByIds(ids: List<String>)
}*/
