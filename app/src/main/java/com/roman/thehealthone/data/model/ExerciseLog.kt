package com.roman.thehealthone.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "exercise_log")
data class ExerciseLog(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val startTime: Long,           // epoch millis
    val endTime: Long,             // epoch millis
    val type: String,
    val distanceMeters: Double = 0.0,
    val calories: Double = 0.0,
    val durationMinutes: Int,
    val source: LogSource,
    val isDeleted: Boolean = false
)

