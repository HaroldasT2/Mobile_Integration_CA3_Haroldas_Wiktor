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
        enableEdgeToEdge()
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
//Used to create short cuts for the placement
fun HomeGuardApp() {
    Box(modifier = Modifier.fillMaxSize()) {
        ImageBackground()
        ArticleCard(
            title = stringResource(R.string.title_jetpack_compose_tutorial),
            shortDescription = stringResource(R.string.compose_short_desc),
            image1 = painterResource(R.drawable.bg_compose_background),
            image2 = painterResource(R.drawable.microphone),
            modifier = Modifier.fillMaxSize().padding(16.dp)
        )
    }
}

@Composable
private fun ArticleCard(
    title: String,
    shortDescription: String,
    image1: Painter,
    image2: Painter,
    modifier: Modifier = Modifier,
) {



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
        Row (modifier = modifier) {




        }
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