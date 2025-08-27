package com.roman.thehealthone.ui.screens.tabs

import com.roman.thehealthone.ui.components.LogInputField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roman.thehealthone.viewmodel.HealthViewModel
import java.util.Date

@Composable
fun SleepTab(vm: HealthViewModel) {
    val sleepLogs by vm.sleepLogs.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Sleep Logs", style = MaterialTheme.typography.headlineSmall)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(sleepLogs.size) { index ->
                val log = sleepLogs[index]
                Text("From: ${Date(log.startTime)} To: ${Date(log.endTime)}, Source: ${log.source}")
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        LogInputField(label1 = "Start (epoch ms)", label2 = "End (epoch ms)") { start, end ->
            val startLong = start.toLongOrNull()
            val endLong = end.toLongOrNull()
            if (startLong != null && endLong != null) {
                vm.addSleep(startLong, endLong)
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        // Centered button
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { vm.syncWithGoogleHealth() }) {
                Text("Sync from Google Health")
            }
        }
    }
}
