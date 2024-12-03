package com.example.homeguard

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homeguard.ui.CameraPage
import com.example.homeguard.ui.theme.HomeGuardTheme
import com.example.homeguard.ui.BottomNavBar
import com.example.homeguard.ui.NotificationsPage
import com.example.homeguard.ui.SettingsPage
import androidx.compose.runtime.Composable
import androidx.compose.animation.animateColorAsState
import com.example.homeguard.viewmodel.HomeViewModel
import com.example.homeguard.viewmodel.HomeViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = this.application
        val scope = CoroutineScope(Dispatchers.Main)
        setContent {
            HomeGuardTheme {
                HomeGuardApp(application, scope)
            }
        }
    }
}

@Composable
fun ImageBackground(){
    val image = painterResource(R.drawable.homeguard_image)
    Image(
        painter = image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun HomeGuardApp(application: Application, scope: CoroutineScope) {
    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(application, scope)
    )
    LaunchedEffect(Unit) {
        homeViewModel.checkDatabasePopulated()
    }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePage(
                navController = navController,
                title = "HomeGuard",
                image1 = painterResource(R.drawable.bg_compose_background),
                image2 = painterResource(R.drawable.microphone),
                viewModel = homeViewModel
            )
        }
        composable("camera") {
            CameraPage(navController, homeViewModel)
        }
        composable("notifications") {
            NotificationsPage(navController)
        }
        composable("settings") {
            SettingsPage(navController, homeViewModel)
        }
    }
}

@Composable
fun HomePage(
    navController: NavController,
    title: String,
    image1: Painter,
    image2: Painter,
    viewModel: HomeViewModel
) {
    val isButtonGreen by viewModel.isButtonGreen.collectAsState()
    val isSliderOn by viewModel.isSliderOn.collectAsState()

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        containerColor = Color.Black
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 40.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            Image(painter = image1, contentDescription = null, modifier = Modifier.size(300.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isButtonGreen) Color.Green else Color.Gray
                ),
                onClick = {
                    viewModel.toggleButtonColor()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Image(painter = image2, contentDescription = null, modifier = Modifier.width(100.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Voice Changer", color = Color.White)
                Switch(
                    checked = isSliderOn,
                    onCheckedChange = { viewModel.toggleSlider() }
                )
            }
        }
    }
}

@Composable
fun CenteredText(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    HomeGuardTheme {
        ImageBackground()
        HomeGuardApp(application = Application(), scope = CoroutineScope(Dispatchers.Main))
    }
}