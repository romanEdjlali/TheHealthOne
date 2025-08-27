package com.roman.thehealthone.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LogInputField(
    label1: String,
    label2: String,
    onSubmit: (String, String) -> Unit
) {
    var value1 by remember { mutableStateOf("") }
    var value2 by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = value1,
            onValueChange = { value1 = it },
            label = { Text(label1) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray
            )
        )
        OutlinedTextField(
            value = value2,
            onValueChange = { value2 = it },
            label = { Text(label2) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray
            )
        )
        Button(
            onClick = { onSubmit(value1, value2) },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Add Log")
        }
    }
}

@Composable
fun ExerciseInputForm(
    onSubmit: (Long, Long, String, Int, Double?, Double?) -> Unit
) {
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var distance by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = startTime,
            onValueChange = { startTime = it },
            label = { Text("Start Time (epoch ms)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray
            )
        )
        OutlinedTextField(
            value = endTime,
            onValueChange = { endTime = it },
            label = { Text("End Time (epoch ms)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray
            )
        )
        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Exercise Type") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray
            )
        )
        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration (minutes)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray
            )
        )
        OutlinedTextField(
            value = calories,
            onValueChange = { calories = it },
            label = { Text("Calories (kcal)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray
            )
        )
        OutlinedTextField(
            value = distance,
            onValueChange = { distance = it },
            label = { Text("Distance (m)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray
            )
        )

        Button(
            onClick = {
                val startLong = startTime.toLongOrNull()
                val endLong = endTime.toLongOrNull()
                val durationInt = duration.toIntOrNull()
                val calDouble = calories.toDoubleOrNull()
                val distDouble = distance.toDoubleOrNull()

                if (startLong != null && endLong != null && durationInt != null && type.isNotBlank()) {
                    onSubmit(startLong, endLong, type, durationInt, calDouble, distDouble)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Add Exercise Log")
        }
    }
}
