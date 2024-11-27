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
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeGuardApp()
                }
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
            imagePainter = painterResource(R.drawable.bg_compose_background),
            modifier = Modifier.fillMaxSize().padding(16.dp)
        )
    }
}

@Composable
private fun ArticleCard(
    title: String,
    shortDescription: String,
    imagePainter: Painter,
    modifier: Modifier = Modifier,
) {

    var peopleCount by remember { mutableStateOf(0) }
    var yesterdayCount by remember { mutableStateOf(0) }
    var averagePeople by remember { mutableStateOf(0.0) }
    var roundUp by remember { mutableStateOf(false) }

    // A List to store the previous saves for calculating the average
    val savedCounts = remember { mutableListOf<Int>() }

    //Used to change the layout of the app
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            modifier = Modifier.padding(16.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Image(painter = imagePainter, contentDescription = null, Modifier.width(900.dp))

        Text(
            text = stringResource(R.string.people_count, peopleCount),
            modifier = Modifier.padding(16.dp),
            fontSize = 24.sp,
            color = Color.White
        )

        Button(
            onClick = { peopleCount++ }, // Increments the people count
            modifier = Modifier.padding(16.dp)
        ){
            Text(text = stringResource(R.string.increment_button)) // Button text: "Add Person"
        }

        // A button to save the current count for today
        Button(
            onClick = {
                savedCounts.add(peopleCount) // Save the current count
                if (savedCounts.size == 1) {
                    yesterdayCount = peopleCount // First save becomes yesterday's count
                } else {
                    // Calculates the average of all saves
                    val total = savedCounts.sum().toDouble()
                    averagePeople = total / savedCounts.size
                }
                peopleCount = 0 // Resets today's count after saving
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(R.string.save_button)) // Button text: "Save the Number"
        }

        // Switch to display average people when toggled
        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Displays the average number of people if the switch is on
        if (roundUp) {
            Text(
                text = stringResource(R.string.average_people_count, averagePeople),
                fontSize = 24.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(150.dp))
        Row (modifier = modifier) {



            Text(
                text = shortDescription,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Justify,
                color = Color.White

            )
        }
    }

}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.show_average),
            color = Color.White)
        Switch(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundUp,
            onCheckedChange = onRoundUpChanged
        )
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