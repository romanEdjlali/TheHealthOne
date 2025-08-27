package com.roman.thehealthone.repository

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.roman.thehealthone.data.model.ExerciseLog
import com.roman.thehealthone.data.model.LogConflict
import com.roman.thehealthone.data.model.LogSource
import com.roman.thehealthone.data.model.ResolutionLog
import com.roman.thehealthone.data.model.SleepLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

/**
 * Set up HealthConnectClient inside your SyncRepository
 * Read and write records, SleepSessionRecord, ExerciseSessionRecord, etc
 * Use existing SyncRepository.syncGoogleHealth() to bridge Google Health, Health Connect with local database Room, ViewModel and so.
 */

class SyncRepository(private val healthClient: HealthConnectClient) {

    companion object {
        val PERMISSIONS = setOf(
            HealthPermission.getReadPermission(SleepSessionRecord::class),
            HealthPermission.getReadPermission(ExerciseSessionRecord::class),
            HealthPermission.getReadPermission(DistanceRecord::class),
            HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class)
        )
    }

    // --- Stored locally after sync ---
    private var cachedSleepLogs: List<SleepLog> = emptyList()
    private var cachedExerciseLogs: List<ExerciseLog> = emptyList()

    /** Check if all permissions granted */
    suspend fun hasAllPermissions(): Boolean {
        val granted = healthClient.permissionController.getGrantedPermissions()
        return granted.containsAll(PERMISSIONS)
    }

    fun getPermissionsArray(): Array<String> {
        return PERMISSIONS.map { it }.toTypedArray()
    }

    /** Sync sleep and exercise logs from Google Health and cache locally */
    suspend fun syncGoogleHealth(): Pair<List<SleepLog>, List<ExerciseLog>> = withContext(Dispatchers.IO) {

        // --- Sleep ---
        val sleepResp = healthClient.readRecords(
            ReadRecordsRequest(
                SleepSessionRecord::class,
                timeRangeFilter = TimeRangeFilter.after(Instant.EPOCH)
            )
        )
        cachedSleepLogs = sleepResp.records.map { rec ->
            SleepLog(
                startTime = rec.startTime.toEpochMilli(),
                endTime = rec.endTime.toEpochMilli(),
                source = LogSource.GOOGLE_HEALTH
            )
        }

        // --- Exercise ---
        val exerciseResp = healthClient.readRecords(
            ReadRecordsRequest(
                ExerciseSessionRecord::class,
                timeRangeFilter = TimeRangeFilter.after(Instant.EPOCH)
            )
        )
        cachedExerciseLogs = exerciseResp.records.map { rec ->
            val distResp = healthClient.readRecords(
                ReadRecordsRequest(DistanceRecord::class, timeRangeFilter = TimeRangeFilter.between(rec.startTime, rec.endTime))
            )
            val totalDist = distResp.records.sumOf { it.distance.inMeters }

            val calResp = healthClient.readRecords(
                ReadRecordsRequest(TotalCaloriesBurnedRecord::class, timeRangeFilter = TimeRangeFilter.between(rec.startTime, rec.endTime))
            )
            val totalCal = calResp.records.sumOf { it.energy.inKilocalories }

            ExerciseLog(
                startTime = rec.startTime.toEpochMilli(),
                endTime = rec.endTime.toEpochMilli(),
                type = rec.exerciseType.toString(),
                distanceMeters = totalDist,
                calories = totalCal,
                durationMinutes = ((rec.endTime.toEpochMilli() - rec.startTime.toEpochMilli()) / 60000).toInt(),
                source = LogSource.GOOGLE_HEALTH
            )
        }

        Pair(cachedSleepLogs, cachedExerciseLogs)
    }

    /** Compute conflicts from cached logs */
    suspend fun getConflicts(): List<LogConflict> {
        val conflicts = mutableListOf<LogConflict>()

        // Sleep conflicts
        val sleepLogs = cachedSleepLogs // or from DB if available
        for (i in 0 until sleepLogs.size) {
            for (j in i + 1 until sleepLogs.size) {
                val log1 = sleepLogs[i]
                val log2 = sleepLogs[j]
                if (logsOverlap(log1.startTime, log1.endTime, log2.startTime, log2.endTime)) {
                    conflicts.add(LogConflict.SleepConflict(log1, log2))
                }
            }
        }

        // Exercise conflicts
        val exerciseLogs = cachedExerciseLogs
        for (i in 0 until exerciseLogs.size) {
            for (j in i + 1 until exerciseLogs.size) {
                val log1 = exerciseLogs[i]
                val log2 = exerciseLogs[j]
                if (logsOverlap(log1.startTime, log1.endTime, log2.startTime, log2.endTime)) {
                    conflicts.add(LogConflict.ExerciseConflict(log1, log2))
                }
            }
        }

        return conflicts
    }


    /** Return previously resolved conflicts (placeholder) */
    suspend fun getResolutions(): List<ResolutionLog> = emptyList()

    /** Helper function to check overlapping times */
    private fun logsOverlap(start1: Long, end1: Long, start2: Long, end2: Long): Boolean {
        return start1 < end2 && start2 < end1
    }
}




/*
class SyncRepository(context: Context) {

    private val client: HealthConnectClient = HealthConnectClient.getOrCreate(context)

    companion object {
        // ✅ Correct permission API for HC 1.1.0-alpha12
        val PERMISSIONS = setOf(
            HealthPermission.getReadPermission(SleepSessionRecord::class),
            HealthPermission.getReadPermission(ExerciseSessionRecord::class),
        )
    }

    */
/** Check if we already have HC permissions *//*

    suspend fun hasPermissions(): Boolean {
        val granted = client.permissionController.getGrantedPermissions()
        return PERMISSIONS.all { it in granted }
    }

    */
/** Read everything (demo); you can change to a shorter window if you want *//*

    suspend fun syncGoogleHealth(): Pair<List<SleepLog>, List<ExerciseLog>> =
        withContext(Dispatchers.IO) {
            val timeRange = TimeRangeFilter.after(Instant.EPOCH)

            // ---- Sleep ----
            val sleepResp = client.readRecords(
                ReadRecordsRequest(
                    recordType = SleepSessionRecord::class,
                    timeRangeFilter = timeRange
                )
            )

            val sleepLogs = sleepResp.records.map { r ->
                SleepLog(
                    startTime = r.startTime.toEpochMilli(),
                    endTime   = r.endTime.toEpochMilli(),
                    source    = LogSource.GOOGLE_HEALTH
                )
            }

            // ---- Exercise ---- (no total distance/calories in the session itself)
            val exerciseResp = client.readRecords(
                ReadRecordsRequest(
                    recordType = ExerciseSessionRecord::class,
                    timeRangeFilter = timeRange
                )
            )

            val exerciseLogs = exerciseResp.records.map { r ->
                ExerciseLog(
                    startTime        = r.startTime.toEpochMilli(),
                    endTime          = r.endTime.toEpochMilli(),
                    type             = mapExerciseType(r.exerciseType), // safe mapping
                    durationMinutes  = ((r.endTime.epochSecond - r.startTime.epochSecond) / 60).toInt(),
                    calories         = null,               // needs separate reads/aggregation
                    distanceMeters   = null,               // needs separate reads/aggregation
                    source           = LogSource.GOOGLE_HEALTH
                )
            }

            sleepLogs to exerciseLogs
        }

    */
/** Minimal/safe mapper (avoid unavailable constants) *//*

    private fun mapExerciseType(type: Int): String = when (type) {
        ExerciseSessionRecord.EXERCISE_TYPE_RUNNING -> "Running"
        ExerciseSessionRecord.EXERCISE_TYPE_WALKING -> "Walking"
        // Add more if you like; fall back keeps compile safe on all versions:
        else -> "Other($type)"
    }

    */
/** Keep your previous conflict detection here so ViewModel can call it *//*

    suspend fun detectConflicts(
        sleepLogs: List<SleepLog>,
        exerciseLogs: List<ExerciseLog>
    ): List<LogConflict> = withContext(Dispatchers.Default) {
        val conflicts = mutableListOf<LogConflict>()

        // Sleep overlap
        for (i in sleepLogs.indices) {
            for (j in i + 1 until sleepLogs.size) {
                if (sleepLogs[i].startTime < sleepLogs[j].endTime &&
                    sleepLogs[j].startTime < sleepLogs[i].endTime
                ) {
                    conflicts.add(LogConflict.SleepConflict(sleepLogs[i], sleepLogs[j]))
                }
            }
        }

        // Exercise overlap
        for (i in exerciseLogs.indices) {
            for (j in i + 1 until exerciseLogs.size) {
                if (exerciseLogs[i].startTime < exerciseLogs[j].endTime &&
                    exerciseLogs[j].startTime < exerciseLogs[i].endTime
                ) {
                    conflicts.add(LogConflict.ExerciseConflict(exerciseLogs[i], exerciseLogs[j]))
                }
            }
        }

        conflicts
    }
}
*/
