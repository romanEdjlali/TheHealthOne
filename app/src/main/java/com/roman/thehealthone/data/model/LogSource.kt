package com.roman.thehealthone.data.model

enum class LogSource { MANUAL, GOOGLE_HEALTH, SAMSUNG_HEALTH, GARMIN }
enum class LogType { SLEEP, EXERCISE }
enum class SourceType { MANUAL, HEALTH_CONNECT }
enum class ConflictStatus { OPEN, RESOLVED, IGNORED }
enum class ConflictResolution { KEEP_A, KEEP_B, MERGE, EDITED }