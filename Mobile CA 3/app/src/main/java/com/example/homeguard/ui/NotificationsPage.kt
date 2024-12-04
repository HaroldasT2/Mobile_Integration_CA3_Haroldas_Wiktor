package com.example.homeguard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homeguard.R


// Mock Notifications Data
data class Notification(val title: String, val timestamp: String, val description: String)

val mockNotifications = listOf(
    Notification("Motion Detected", "2024-12-02 10:00", "Motion detected at the front door."),
    Notification("Camera Offline", "2024-12-01 22:00", "Camera is currently offline."),
    Notification("Settings Updated", "2024-11-30 14:30", "You updated the Voice Changer settings.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsPage(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No notifications yet.",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun NotificationCard(notification: Notification) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = notification.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = notification.timestamp,
                fontSize = 12.sp,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = notification.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
