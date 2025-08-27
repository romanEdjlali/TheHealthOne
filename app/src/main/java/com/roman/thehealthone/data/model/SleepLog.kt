package com.roman.thehealthone.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "sleep_log")
data class SleepLog(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val startTime: Long, // epoch millis
    val endTime: Long, // epoch millis
    val source: LogSource,
    val notes: String? = null,
    val isDeleted: Boolean = false,
)
