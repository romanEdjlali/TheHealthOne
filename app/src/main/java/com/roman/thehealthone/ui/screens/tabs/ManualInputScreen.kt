package com.roman.thehealthone.ui.screens.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.roman.thehealthone.viewmodel.HealthViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ManualInputScreen(vm: HealthViewModel) {
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var distance by remember { mutableStateOf("") }

    var isSleepMode by remember { mutableStateOf(true) }

    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            if (isSleepMode) "Add Sleep Session" else "Add Exercise Session",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Spacer(Modifier.height(16.dp))

        // Toggle between Sleep/Exercise
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { isSleepMode = true },
                colors = ButtonDefaults.buttonColors(containerColor = if (isSleepMode) Color.Gray else Color.LightGray)
            ) { Text("Sleep") }

            Button(
                onClick = { isSleepMode = false },
                colors = ButtonDefaults.buttonColors(containerColor = if (!isSleepMode) Color.Gray else Color.LightGray)
            ) { Text("Exercise") }
        }

        Spacer(Modifier.height(16.dp))

        // Common Start/End time fields
        OutlinedTextField(
            value = startTime,
            onValueChange = { startTime = it },
            label = { Text("Start Time (yyyy-MM-dd HH:mm)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = endTime,
            onValueChange = { endTime = it },
            label = { Text("End Time (yyyy-MM-dd HH:mm)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(Modifier.height(8.dp))

        if (!isSleepMode) {
            // Exercise specific fields
            OutlinedTextField(
                value = type,
                onValueChange = { type = it },
                label = { Text("Type (e.g., Running)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Gray
                )
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duration (minutes)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Gray
                )
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = calories,
                onValueChange = { calories = it },
                label = { Text("Calories (optional)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Gray
                )
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = distance,
                onValueChange = { distance = it },
                label = { Text("Distance (meters, optional)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Gray
                )
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                try {
                    val startMillis = dateFormat.parse(startTime)?.time ?: return@Button
                    val endMillis = dateFormat.parse(endTime)?.time ?: return@Button

                    if (isSleepMode) {
                        vm.addSleep(startMillis, endMillis)
                    } else {
                        val dur = duration.toIntOrNull() ?: 0
                        val cal = calories.toDoubleOrNull()
                        val dist = distance.toDoubleOrNull()
                        vm.addExercise(type, dur, startMillis, endMillis, cal, dist)
                    }

                    // Clear fields after adding
                    startTime = ""
                    endTime = ""
                    type = ""
                    duration = ""
                    calories = ""
                    distance = ""
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add ${if (isSleepMode) "Sleep" else "Exercise"}")
        }
    }
}
