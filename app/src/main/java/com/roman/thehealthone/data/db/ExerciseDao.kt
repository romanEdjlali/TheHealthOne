package com.roman.thehealthone.data.db

import androidx.room.*
import com.roman.thehealthone.data.model.ExerciseLog


@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise_log WHERE isDeleted = 0 ORDER BY startTime DESC")
    suspend fun getAll(): List<ExerciseLog>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: ExerciseLog)

    @Update
    suspend fun update(log: ExerciseLog)

    @Delete
    suspend fun delete(log: ExerciseLog)

    // Optional: mark log as deleted instead of hard delete
    @Query("UPDATE exercise_log SET isDeleted = 1 WHERE id = :id")
    suspend fun softDelete(id: String)
}
