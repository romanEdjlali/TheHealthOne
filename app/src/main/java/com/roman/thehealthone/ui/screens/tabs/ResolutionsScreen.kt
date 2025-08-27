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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.roman.thehealthone.viewmodel.HealthViewModel

@Composable
fun ResolutionsScreen(vm: HealthViewModel) {
    val resolutions by vm.resolutions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            "Resolved Conflicts",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Spacer(Modifier.height(16.dp))

        if (resolutions.isEmpty()) {
            Text("No resolved conflicts yet.", color = Color.DarkGray)
        } else {
            LazyColumn {
                items(resolutions) { resolution ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Resolved Group: ${resolution.groupKey}", color = Color.Black)
                            Text("Winner ID: ${resolution.winnerId}", color = Color.Green)
                        }
                    }
                }
            }
        }
    }
}
