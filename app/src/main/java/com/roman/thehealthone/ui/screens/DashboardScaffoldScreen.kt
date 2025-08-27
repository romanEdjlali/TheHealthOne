package com.roman.thehealthone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.roman.thehealthone.NavRoute
import com.roman.thehealthone.R
import com.roman.thehealthone.repository.SyncRepository
import com.roman.thehealthone.ui.screens.tabs.ConflictsScreen
import com.roman.thehealthone.ui.screens.tabs.LogsScreen
import com.roman.thehealthone.ui.screens.tabs.ManualInputScreen
import com.roman.thehealthone.ui.screens.tabs.ResolutionsScreen
import com.roman.thehealthone.ui.screens.tabs.SyncScreen
import com.roman.thehealthone.viewmodel.HealthViewModel

@Composable
fun DashboardScaffoldScreen(
    navController: NavController,
    vm: HealthViewModel,
    syncRepo: SyncRepository
) {
    var selectedTab by remember { mutableStateOf<NavRoute>(NavRoute.Sync) } // Default = Sync

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                listOf(
                    NavRoute.Sync,
                    NavRoute.Logs,
                    NavRoute.Conflicts,
                    NavRoute.Resolutions,
                    NavRoute.Manual
                ).forEach { item ->
                    NavigationBarItem(
                        selected = selectedTab == item,
                        onClick = { selectedTab = item },
                        label = {
                            Text(
                                text = item.label,
                                color = if (selectedTab == item) Color.Black else Color.DarkGray
                            )
                        },
                        icon = {
                            Image(
                                painter = painterResource(
                                    id = when (item.icon) {
                                        "sync" -> R.drawable.ic_sync
                                        "logs" -> R.drawable.ic_log
                                        "errors" -> R.drawable.ic_conflict
                                        "fixed" -> R.drawable.ic_fixed
                                        "add" -> R.drawable.ic_add
                                        else -> R.drawable.ic_default
                                    }
                                ),
                                contentDescription = item.label
                            )
                        }
                    )
                }
            }
        }
    ) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            when (selectedTab) {
                NavRoute.Sync -> SyncScreen(vm, syncRepo)
                NavRoute.Logs -> LogsScreen(vm)
                NavRoute.Conflicts -> ConflictsScreen(vm)
                NavRoute.Resolutions -> ResolutionsScreen(vm)
                NavRoute.Manual -> ManualInputScreen(vm)
            }
        }
    }
}


