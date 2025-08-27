package com.roman.thehealthone.ui.screens

import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roman.thehealthone.viewmodel.HealthViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HealthViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Health App", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate("sleep") }, modifier = Modifier.fillMaxWidth()) {
            Text("View Sleep Logs")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.navigate("exercise") }, modifier = Modifier.fillMaxWidth()) {
            Text("View Exercise Logs")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.navigate("conflicts") }, modifier = Modifier.fillMaxWidth()) {
            Text("Resolve Conflicts")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.syncWithGoogleHealth() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Sync with Google Health")
        }
    }
}
