package com.roman.thehealthone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.roman.thehealthone.data.model.*
import com.roman.thehealthone.repository.HealthRepository
import com.roman.thehealthone.repository.SyncRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HealthViewModel(
    private val repository: HealthRepository,
    private val syncRepository: SyncRepository
) : ViewModel() {

    private val _sleepLogs = MutableStateFlow<List<SleepLog>>(emptyList())
    val sleepLogs: StateFlow<List<SleepLog>> = _sleepLogs

    private val _exerciseLogs = MutableStateFlow<List<ExerciseLog>>(emptyList())
    val exerciseLogs: StateFlow<List<ExerciseLog>> = _exerciseLogs

    private val _conflicts = MutableStateFlow<List<LogConflict>>(emptyList())
    val conflicts: StateFlow<List<LogConflict>> = _conflicts

    private val _resolutions = MutableStateFlow<List<ResolutionLog>>(emptyList())
    val resolutions: StateFlow<List<ResolutionLog>> = _resolutions

    val logs: StateFlow<List<UiLog>> = combine(_sleepLogs, _exerciseLogs) { sleeps, exercises ->
        val sleepUi = sleeps.map {
            UiLog(
                type = "Sleep",
                details = "From ${it.startTime} to ${it.endTime}"
            )
        }
        val exerciseUi = exercises.map {
            UiLog(
                type = "Exercise (${it.type})",
                details = "${it.durationMinutes} min, ${it.calories} kcal, ${it.distanceMeters} m"
            )
        }
        sleepUi + exerciseUi
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init { refreshData() }

    private fun refreshData() = viewModelScope.launch {
        _sleepLogs.value = repository.getAllSleep()
        _exerciseLogs.value = repository.getAllExercise()
        _conflicts.value = syncRepository.getConflicts()
        _resolutions.value = syncRepository.getResolutions() // 👈 add this if repo supports it
    }

    fun addSleep(start: Long, end: Long) = viewModelScope.launch {
        repository.addSleep(SleepLog(startTime = start, endTime = end, source = LogSource.MANUAL))
        refreshData()
    }

    /**
     * Accept nullable calories/distance from the UI, but convert to non-nullable values
     * when creating the ExerciseLog (avoids `required Double found Double?`).
     */
    fun addExercise(
        type: String,
        duration: Int,
        startTime: Long,
        endTime: Long,
        calories: Double?,   // nullable from UI
        distance: Double?    // nullable from UI
    ) = viewModelScope.launch {
        // Provide safe defaults if the UI passed nulls
        val calValue: Double = calories ?: 0.0
        val distValue: Double = distance ?: 0.0

        val exercise = ExerciseLog(
            startTime = startTime,
            endTime = endTime,
            type = type,
            durationMinutes = duration,
            calories = calValue,       // non-nullable Double
            distanceMeters = distValue,// non-nullable Double
            source = LogSource.MANUAL
        )

        repository.addExercise(exercise)
        refreshData()
    }

    fun syncWithGoogleHealth() = viewModelScope.launch {
        val (sleepLogs, exerciseLogs) = syncRepository.syncGoogleHealth()
        sleepLogs.forEach { repository.addSleep(it) }
        exerciseLogs.forEach { repository.addExercise(it) }
        refreshData()
    }

    fun resolveConflict(conflict: LogConflict) {
        _conflicts.value = _conflicts.value - conflict
    }

    companion object {
        fun provideFactory(
            repository: HealthRepository,
            syncRepository: SyncRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HealthViewModel(repository, syncRepository) as T
            }
        }
    }
}

