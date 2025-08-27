package com.roman.thehealthone.data.model

// A generic conflict wrapper for logs of the same type
sealed class LogConflict {
    data class SleepConflict(val log1: SleepLog, val log2: SleepLog) : LogConflict()
    data class ExerciseConflict(val log1: ExerciseLog, val log2: ExerciseLog) : LogConflict()
}
