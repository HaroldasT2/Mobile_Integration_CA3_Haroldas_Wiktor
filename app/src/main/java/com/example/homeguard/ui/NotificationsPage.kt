package com.example.homeguard.ui

import android.app.Application
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homeguard.viewmodel.HomeViewModel
import com.example.homeguard.viewmodel.HomeViewModelFactory
import com.example.homeguard.network.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsPage(
    navController: NavController,
    application: Application
) {
    // Uses the custom ViewModelFactory
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(application, CoroutineScope(Dispatchers.IO))
    )

    val notifications by viewModel.notifications.observeAsState(emptyList())

    // Triggers fetchNotifications when the screen is loaded
    LaunchedEffect(Unit) {
        viewModel.fetchNotifications()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Notifications") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = { BottomNavBar(navController) },
        containerColor = Color.Black
    ) { paddingValues ->
        if (notifications.isEmpty()) {
            // Shows "No notifications yet" message when there are no notifications
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
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Shows a list of notifications
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(notifications) { notification ->
                    NotificationCard(notification)
                }
            }
        }
    }
}

@Composable
fun NotificationCard(notification: Notification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = notification.title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Text(
                text = notification.message,
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )

        }
    }
}