package com.example.homeguard.ui


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.homeguard.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable


// Data class for Motion Events
data class MotionEvent(
    val id: Int,
    val timestamp: String,
    val description: String,
    val thumbnail: Int
)

// Mock Data
val mockMotionEvents = listOf(
    MotionEvent(1, "2024-12-01 10:00", "Motion detected near the front door", R.drawable.ic_launcher_foreground),
    MotionEvent(2, "2024-12-01 10:05", "Motion detected near the garage", R.drawable.ic_launcher_foreground),
    MotionEvent(3, "2024-12-01 10:10", "Motion detected near the backyard", R.drawable.ic_launcher_foreground)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraPage(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        containerColor = Color.Black
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Camera Recordings",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(mockMotionEvents.size) { index ->
                    val event = mockMotionEvents[index]
                    CameraEventCard(event)
                }
            }
        }
    }
}



@Composable
fun CameraEventCard(event: MotionEvent) {
    var expanded by remember { mutableStateOf(false) } // Track expanded state

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }, // Toggle expanded state on click
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = event.thumbnail),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = event.timestamp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = event.description,
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }
            }

            // Expandable Section
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Divider(color = Color.Gray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Recording Details: ${event.id}",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


