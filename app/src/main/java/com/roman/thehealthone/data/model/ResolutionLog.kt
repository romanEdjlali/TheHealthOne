package com.roman.thehealthone.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Optional: store a canonical row that points to the winner for quick queries
@Entity(tableName = "resolution_log")
data class ResolutionLog(
    @PrimaryKey val groupKey: String, //hash of overlapping range & day
    val winnerId: String,
)