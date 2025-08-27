package com.roman.thehealthone.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.roman.thehealthone.repository.SyncRepository
import com.roman.thehealthone.ui.screens.DashboardScaffoldScreen
import com.roman.thehealthone.ui.screens.LoginScreen
import com.roman.thehealthone.viewmodel.HealthViewModel

@Composable
fun HealthApp(vm: HealthViewModel, syncRepo: SyncRepository)  {
    Navigation(vm, syncRepo)
}

@Composable
fun Navigation(vm: HealthViewModel, syncRepo: SyncRepository) {  // add syncRepo here
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(navController = navController)
        }
        composable("LandingScaffold") {
            DashboardScaffoldScreen(
                navController = navController,
                vm = vm,
                syncRepo = syncRepo // ✅ pass syncRepo
            )
        }
    }
}
