package com.example.homeguard.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.homeguard.R
import com.example.homeguard.Recording
import com.example.homeguard.viewmodel.HomeViewModel
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraPage(navController: NavController, viewModel: HomeViewModel) {
    var recordings by remember { mutableStateOf(listOf<Recording>()) }

    LaunchedEffect(Unit) {
        viewModel.getRecordings { fetchedRecordings ->
            recordings = fetchedRecordings
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        containerColor = Color.Black
    ) { paddingValues ->
        BoxWithConstraints {
            val isLargeScreen = maxWidth > 600.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Camera Recordings",
                    fontSize = if (isLargeScreen) 30.sp else 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(recordings) { recording ->
                        ExpandableCameraEventCard(recording, isLargeScreen)
                    }
                }
            }
        }
    }
}


@Composable
fun ExpandableCameraEventCard(recording: Recording, isLargeScreen: Boolean) {
    var isExpanded by remember { mutableStateOf(false) }

    Log.d("ExpandableCameraEventCard", "Image URL: ${recording.imageUrl}")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .animateContentSize()
            .height(if (isExpanded) (if (isLargeScreen) 200.dp else 160.dp) else (if (isLargeScreen) 120.dp else 80.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = recording.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(if (isLargeScreen) 80.dp else 64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop,
                    onLoading = { Log.d("CameraPage", "Loading image: ${recording.imageUrl}") },
                    onError = { Log.e("CameraPage", "Failed to load image: ${recording.imageUrl}") },
                    onSuccess = { Log.d("CameraPage", "Successfully loaded image: ${recording.imageUrl}") }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = recording.timestamp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = if (isLargeScreen) 18.sp else 16.sp
                    )
                    Text(
                        text = recording.description,
                        color = Color.LightGray,
                        fontSize = if (isLargeScreen) 16.sp else 14.sp
                    )
                }
            }
            if (isExpanded) {
                Text(
                    text = "Additional Details for Recording ID: ${recording.id}",
                    color = Color.White,
                    fontSize = if (isLargeScreen) 16.sp else 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
