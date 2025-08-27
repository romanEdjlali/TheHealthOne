package com.roman.healthapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roman.thehealthone.ui.components.ExerciseInputForm
import com.roman.thehealthone.viewmodel.HealthViewModel

@Composable
fun ExerciseTab(vm: HealthViewModel) {
    val exerciseLogs by vm.exerciseLogs.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Exercise Logs", style = MaterialTheme.typography.headlineSmall)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(exerciseLogs.size) { index ->
                val log = exerciseLogs[index]
                Text("${log.type}: ${log.durationMinutes} min, ${log.calories ?: 0.0} cal")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ExerciseInputForm { start, end, type, duration, calories, distance ->
            vm.addExercise(type, duration, start, end, calories, distance)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { vm.syncWithGoogleHealth() }) {
            Text("Sync from Google Health")
        }
    }
}


