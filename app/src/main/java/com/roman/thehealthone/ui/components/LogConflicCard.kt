package com.roman.thehealthone.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roman.thehealthone.data.model.LogConflict

@Composable
fun LogConflictCard(
    conflict: LogConflict,
    onResolve: (Any) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            when (conflict) {
                is LogConflict.SleepConflict -> {
                    Text("Sleep Conflict Detected", style = MaterialTheme.typography.titleMedium)
                    Text("Option 1: ${conflict.log1.startTime} - ${conflict.log1.endTime}")
                    Text("Option 2: ${conflict.log2.startTime} - ${conflict.log2.endTime}")
                    Row {
                        Button(onClick = { onResolve(conflict.log1) }, modifier = Modifier.weight(1f)) {
                            Text("Keep Log 1")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { onResolve(conflict.log2) }, modifier = Modifier.weight(1f)) {
                            Text("Keep Log 2")
                        }
                    }
                }

                is LogConflict.ExerciseConflict -> {
                    Text("Exercise Conflict Detected", style = MaterialTheme.typography.titleMedium)
                    Text("Option 1: ${conflict.log1.type}, ${conflict.log1.durationMinutes} min")
                    Text("Option 2: ${conflict.log2.type}, ${conflict.log2.durationMinutes} min")
                    Row {
                        Button(onClick = { onResolve(conflict.log1) }, modifier = Modifier.weight(1f)) {
                            Text("Keep Log 1")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { onResolve(conflict.log2) }, modifier = Modifier.weight(1f)) {
                            Text("Keep Log 2")
                        }
                    }
                }
            }
        }
    }
}
