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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.roman.thehealthone.viewmodel.HealthViewModel

@Composable
fun LogsScreen(vm: HealthViewModel) {
    val logs by vm.logs.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text("Logs", style = MaterialTheme.typography.headlineMedium, color = Color.Black)
        Spacer(Modifier.height(16.dp))

        if (logs.isEmpty()) {
            Text("No logs yet", color = Color.DarkGray)
        } else {
            LazyColumn {
                items(logs) { log ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(log.type, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
                            Text(log.details, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
                        }
                    }
                }
            }
        }
    }
}


