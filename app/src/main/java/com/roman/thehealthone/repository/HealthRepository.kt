package com.roman.thehealthone.repository

import com.roman.thehealthone.data.model.ExerciseLog
import com.roman.thehealthone.data.model.SleepLog
import com.roman.thehealthone.data.db.ExerciseDao
import com.roman.thehealthone.data.db.ResolutionDao
import com.roman.thehealthone.data.db.SleepDao
import com.roman.thehealthone.data.model.ResolutionLog

class HealthRepository(
    private val sleepDao: SleepDao,
    private val exerciseDao: ExerciseDao,
    private val resolutionDao: ResolutionDao
) {
    suspend fun addSleep(log: SleepLog) = sleepDao.insert(log)
    suspend fun addExercise(log: ExerciseLog) = exerciseDao.insert(log)

    suspend fun getAllSleep(): List<SleepLog> = sleepDao.getAll()
    suspend fun getAllExercise(): List<ExerciseLog> = exerciseDao.getAll()

    suspend fun softDeleteSleep(id: String) = sleepDao.softDelete(id)
    suspend fun softDeleteExercise(id: String) = exerciseDao.softDelete(id)

    suspend fun addResolution(resolution: ResolutionLog) {
        resolutionDao.insert(resolution)
    }

    suspend fun getAllResolutions(): List<ResolutionLog> {
        return resolutionDao.getAll()
    }
}

