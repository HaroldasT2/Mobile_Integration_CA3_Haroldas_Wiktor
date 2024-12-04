package com.example.homeguard.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homeguard.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(navController: NavController, viewModel: HomeViewModel) {
    val isSliderOn by viewModel.isSliderOn.collectAsState()
    Log.d("SettingsPage", "Slider state in SettingsPage: $isSliderOn")
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Settings",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )


            SettingToggle(
                title = "Green Button Enabled",
                isChecked = viewModel.isButtonGreen.collectAsState(initial = false).value,
                onToggle = { viewModel.toggleButtonColor() }
            )


            SettingToggle(
                title = "Slider Enabled",
                isChecked = viewModel.isSliderOn.collectAsState(initial = false).value,
                onToggle = { viewModel.toggleSlider() }
            )
        }
    }
}

@Composable
fun SettingToggle(title: String, isChecked: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = Color.White)
        Switch(
            checked = isChecked,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = Color.Gray
            )
        )
    }
}
