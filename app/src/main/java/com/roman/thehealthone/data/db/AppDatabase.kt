package com.roman.thehealthone.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.roman.thehealthone.data.model.ConflictLog
import com.roman.thehealthone.data.model.ExerciseLog
import com.roman.thehealthone.data.model.ResolutionLog
import com.roman.thehealthone.data.model.SleepLog

// AppDatabase.kt
@Database(entities = [SleepLog::class, ExerciseLog::class, ConflictLog::class, ResolutionLog::class,], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sleepDao(): SleepDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun conflictDao(): ConflictDao
    abstract fun resolutionDao(): ResolutionDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "app-db"
                            ).fallbackToDestructiveMigration(false)
                    .build().also { INSTANCE = it }
            }
    }
}


