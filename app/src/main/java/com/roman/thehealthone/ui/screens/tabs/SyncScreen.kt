package com.roman.thehealthone.ui.screens.tabs

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.roman.healthapp.ui.screens.ExerciseTab
import com.roman.thehealthone.repository.SyncRepository
import com.roman.thehealthone.viewmodel.HealthViewModel


@Composable
fun SyncScreen(vm: HealthViewModel, syncRepo: SyncRepository) {
    var hasPermission by remember { mutableStateOf(false) }

    // Launcher to request permissions
    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        hasPermission = results.values.all { it }
    }

    LaunchedEffect(Unit) {
        // Check if already granted
        val granted = syncRepo.hasAllPermissions()
        hasPermission = granted
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Your tab content
        var selectedTab by remember { mutableIntStateOf(0) }
        val tabs = listOf("Sleep", "Exercise", "Conflicts")

        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTab) {
            0 -> SleepTab(vm)
            1 -> ExerciseTab(vm)
            2 -> ConflictsTab(vm)
        }
    }
}

