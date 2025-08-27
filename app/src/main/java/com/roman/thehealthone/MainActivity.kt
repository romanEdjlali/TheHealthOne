package com.roman.thehealthone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.HealthConnectClient
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.roman.thehealthone.data.db.AppDatabase
import com.roman.thehealthone.repository.HealthRepository
import com.roman.thehealthone.repository.SyncRepository
import com.roman.thehealthone.ui.nav.HealthApp
import com.roman.thehealthone.ui.theme.TheHealthOneTheme
import com.roman.thehealthone.viewmodel.HealthViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // HealthConnectClient
        val healthClient = HealthConnectClient.getOrCreate(this)
        val syncRepo = SyncRepository(healthClient)

        // Initialize database via singleton
        val db = AppDatabase.getInstance(applicationContext)

        // Initialize repositories
        val healthRepository = HealthRepository(
            sleepDao = db.sleepDao(),
            exerciseDao = db.exerciseDao(),
            resolutionDao = db.resolutionDao()
        )

        enableEdgeToEdge()

        setContent {
            val viewModel: HealthViewModel = viewModel(
                factory = HealthViewModel.provideFactory(healthRepository, syncRepo)
            )

            TheHealthOneTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Welcome text
                            Greeting(
                                name = "Welcome to the HealthApp",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 50.dp)
                            )

                            // Instruction text
                            Text(
                                text = "Enter any username & password to login!",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Normal
                                ),
                                color = Color.Blue,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                            )
                        }

                        // Main App Navigation
                        HealthApp(vm = viewModel, syncRepo = syncRepo)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
        modifier = modifier,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

