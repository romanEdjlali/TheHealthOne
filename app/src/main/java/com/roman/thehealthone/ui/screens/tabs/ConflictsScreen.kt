package com.roman.thehealthone.ui.screens.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.roman.thehealthone.data.model.LogConflict
import com.roman.thehealthone.viewmodel.HealthViewModel


@Composable
fun ConflictsScreen(vm: HealthViewModel) {
    val conflicts by vm.conflicts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            "Conflicts",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Spacer(Modifier.height(16.dp))

        if (conflicts.isEmpty()) {
            Text("No conflicts detected!", color = Color.DarkGray)
        } else {
            LazyColumn {
                items(conflicts) { conflict ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            when (conflict) {
                                is LogConflict.SleepConflict -> {
                                    Text(
                                        "Sleep Conflict",
                                        color = Color.Black
                                    )
                                    Text(
                                        "Session A: ${conflict.log1.id} (${conflict.log1.source})",
                                        color = Color.Red
                                    )
                                    Text(
                                        "Session B: ${conflict.log2.id} (${conflict.log2.source})",
                                        color = Color.Blue
                                    )
                                }
                                is LogConflict.ExerciseConflict -> {
                                    Text(
                                        "Exercise Conflict",
                                        color = Color.Black
                                    )
                                    Text(
                                        "Session A: ${conflict.log1.id} (${conflict.log1.source})",
                                        color = Color.Red
                                    )
                                    Text(
                                        "Session B: ${conflict.log2.id} (${conflict.log2.source})",
                                        color = Color.Blue
                                    )
                                }
                            }

                            Spacer(Modifier.height(8.dp))

                            Button(
                                onClick = { vm.resolveConflict(conflict) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Resolve")
                            }
                        }
                    }
                }
            }
        }
    }
}



/*@Composable
fun ConflictsScreen(vm: HealthViewModel) {
    val conflicts by vm.conflicts.collectAsState() // List<LogConflict>

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Conflicts",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (conflicts.isEmpty()) {
            Text("No conflicts detected 🎉", color = Color.DarkGray)
        } else {
            LazyColumn {
                items(conflicts) { conflict ->
                    ConflictCard(conflict = conflict, vm = vm)
                }
            }
        }
    }
}

@Composable
fun ConflictCard(conflict: LogConflict, vm: HealthViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            when (conflict) {
                is LogConflict.SleepConflict -> {
                    Text(
                        text = "Sleep Conflict",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Session A: ${Date(conflict.log1.startTime)} - ${Date(conflict.log1.endTime)}",
                        color = Color.Red
                    )
                    Text(
                        text = "Session B: ${Date(conflict.log2.startTime)} - ${Date(conflict.log2.endTime)}",
                        color = Color.Blue
                    )
                }
                is LogConflict.ExerciseConflict -> {
                    Text(
                        text = "Exercise Conflict",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Session A: ${conflict.log1.type} (${conflict.log1.startTime} - ${conflict.log1.endTime})",
                        color = Color.Red
                    )
                    Text(
                        text = "Session B: ${conflict.log2.type} (${conflict.log2.startTime} - ${conflict.log2.endTime})",
                        color = Color.Blue
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { vm.resolveConflict(conflict) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Resolve")
            }
        }
    }
}*/
