package com.example.homeguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homeguard.ui.theme.HomeGuardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeGuardTheme {
                HomeGuardApp()
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
fun HomeGuardApp() {
    val navController = rememberNavController()
    ImageBackground()
    NavHost(navController = navController, startDestination = "home") {
        //Home Page
        composable("home") { HomePage(navController,
            title = stringResource(R.string.title_jetpack_compose_tutorial),
            image1 = painterResource(R.drawable.bg_compose_background),
            image2 = painterResource(R.drawable.microphone),
            modifier = Modifier.fillMaxSize().padding(16.dp)) }



        //Camera Page
        composable("camera") { CameraPage(navController) }


        //Notifications Page
        composable("notifications") { NotificationsPage(navController) }


        //Settings Page
        composable("settings") { SettingsPage(navController) }
    }
}

@Composable
fun HomePage(navController: androidx.navigation.NavController,
             title: String,
             image1: Painter,
             image2: Painter,
             modifier: Modifier = Modifier,) {



    var isGreen by remember { mutableStateOf(false) }
    var isSwitchChecked by remember { mutableStateOf(false) }



    //Used to change the layout of the app
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            modifier = Modifier.padding(16.dp),
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Image(painter = image1, contentDescription = null, modifier = Modifier.size(300.dp))



        Button(
            colors = ButtonDefaults.buttonColors(containerColor = if (isGreen) Color.Green else Color.Gray),
            onClick = { isGreen = !isGreen },
            modifier = Modifier.padding(16.dp)
        ){
            // Text(text = stringResource(R.string.increment_button)) // Button text: "Add Person"
            Image(painter = image2, contentDescription = null, Modifier.width(100.dp))
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.show_average),
                color = Color.White)
            Switch(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End),
                checked = isSwitchChecked,
                onCheckedChange = { isSwitchChecked = it }
            )
        }



        Spacer(modifier = Modifier.height(150.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigate("home") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { navController.navigate("camera") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Camera",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { navController.navigate("notifications") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bell),
                    contentDescription = "Bell",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { navController.navigate("settings") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Settings",
                    tint = Color.Black
                )
            }
        }

    }

}

@Composable
fun CameraPage(navController: androidx.navigation.NavController) {
    CenteredText("Camera Page")
}

@Composable
fun NotificationsPage(navController: androidx.navigation.NavController) {
    CenteredText("Notifications Page")
}

@Composable
fun SettingsPage(navController: androidx.navigation.NavController) {
    CenteredText("Settings Page")
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
        HomeGuardApp()

    }
}
