package com.example.homeguard.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import com.example.homeguard.R

@Composable
fun BottomNavBar(navController: NavController) {
    BottomAppBar(
        containerColor = Color.White, // Background color
        contentColor = Color.Black    // Icon color
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    tint = Color.Black
                )
            }
            IconButton(onClick = { navController.navigate("camera") }) {
                Icon(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Camera",
                    tint = Color.Black
                )
            }
            IconButton(onClick = { navController.navigate("notifications") }) {
                Icon(
                    painter = painterResource(id = R.drawable.bell),
                    contentDescription = "Notifications",
                    tint = Color.Black
                )
            }
            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Settings",
                    tint = Color.Black
                )
            }
        }
    }
}
