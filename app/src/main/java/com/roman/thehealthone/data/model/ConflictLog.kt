package com.roman.thehealthone.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conflict_log")
data class ConflictLog(
    @PrimaryKey(autoGenerate = true) val conflictId: Long = 0,
    val kind: String,                // "SLEEP" or "EXERCISE"
    val leftId: String,              // session id A
    val rightId: String,             // session id B
    val reason: String,              // e.g. "TIME_OVERLAP"
    val resolvedWithId: String? = null, // winner’s id
    val status: ConflictStatus = ConflictStatus.OPEN,
    val startTime: Long,
    val endTime: Long
)