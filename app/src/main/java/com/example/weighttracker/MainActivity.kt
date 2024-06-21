
package com.example.weighttracker

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.weighttracker.ui.theme.WeightTrackerTheme
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "weight-tracker-database"
        ).fallbackToDestructiveMigration() // Use carefully in production!
            .build()

        val viewModelFactory = WeightViewModelFactory(db.weightDao())

        setContent {
            WeightTrackerTheme {
                val viewModel: WeightViewModel = viewModel(factory = viewModelFactory)
                val lifecycleOwner = LocalLifecycleOwner.current
                val weights by viewModel.allWeights.collectAsStateWithLifecycle(
                    lifecycle = lifecycleOwner.lifecycle,
                    minActiveState = Lifecycle.State.STARTED
                )

                var weightInput by remember { mutableStateOf("") }
                var weightIdInput by remember { mutableStateOf("") }
                val context = LocalContext.current

                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Weight Tracker") })
                    },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = weightInput,
                                onValueChange = { weightInput = it },
                                label = { Text("Enter Weight") }
                            )
                            OutlinedTextField(
                                value = weightIdInput,
                                onValueChange = { weightIdInput = it },
                                label = { Text("Enter Weight ID (for update/delete)") }
                            )
                            Button(onClick = {
                                val weightValue = weightInput.toFloatOrNull()
                                if (weightValue != null) {
                                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                                    viewModel.addWeight(Weight(weight = weightValue, date = currentDate))
                                    weightInput = ""
                                } else {
                                    Toast.makeText(context, "Invalid weight input", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Text("Add Weight")
                            }
                            Button(onClick = {
                                val id = weightIdInput.toIntOrNull()
                                val weight = weightInput.toFloatOrNull()
                                if (id != null && weight != null) {
                                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                                    viewModel.updateWeight(Weight(id = id, weight = weight, date = currentDate))
                                    weightInput = ""
                                    weightIdInput = ""
                                } else {
                                    Toast.makeText(context, "Invalid ID or weight input", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Text("Update Weight")
                            }
                            Button(onClick = {
                                val id = weightIdInput.toIntOrNull()
                                if (id != null) {
                                    viewModel.deleteWeight(Weight(id = id, weight = 0f, date = "")) // Dummy weight for deletion
                                    weightIdInput = ""
                                } else {
                                    Toast.makeText(context, "Invalid ID input", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Text("Delete Weight")
                            }

                            // Display weights using LazyColumn
                            LazyColumn {
                                items(weights) { weight ->
                                    Text("ID: ${weight.id}, Weight: ${weight.weight}, Date: ${weight.date}")
                                }
                            }
                        }
                    }
                )
            }
        }

        // Check SMS permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestSmsPermission()
        }
    }

    private fun requestSmsPermission() {
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, you can now send SMS if needed
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeightTrackerTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Enter Weight") }
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Enter Weight ID (for update/delete)") }
            )
            Button(onClick = {}) {
                Text("Add Weight")
            }
            Button(onClick = {}) {
                Text("Update Weight")
            }
            Button(onClick = {}) {
                Text("Delete Weight")
            }
        }
    }
}
