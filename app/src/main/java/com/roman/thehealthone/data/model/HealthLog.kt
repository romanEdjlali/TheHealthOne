package com.roman.thehealthone.data.model

data class HealthLog(
    val id: String,
    val type: String,       // "Sleep" or exercise type like "Running"
    val details: String,    // time range, calories, distance etc.
    val startTime: Long,
    val endTime: Long
)
