package com.roman.thehealthone.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roman.thehealthone.data.model.ResolutionLog

@Dao
interface ResolutionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resolution: ResolutionLog)

    @Query("SELECT * FROM resolution_log")
    suspend fun getAll(): List<ResolutionLog>
}
