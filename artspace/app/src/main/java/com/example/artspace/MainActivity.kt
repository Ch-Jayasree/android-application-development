package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtspaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtspaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ArtSpaceScreen(modifier: Modifier = Modifier) {
    // State Tracker: Dictates exactly which masterwork canvas page index is active
    var currentStep by remember { mutableStateOf(1) }
    val totalSteps = 3

    // Dynamic Asset Bindings: Mapping strings and drawables cleanly based on active index
    val imageResource = when (currentStep) {
        1 -> R.drawable.starry_night
        2 -> R.drawable.mona_lisa
        else -> R.drawable.pink_cloud
    }

    val titleText = when (currentStep) {
        1 -> "The Starry Night"
        2 -> "Mona Lisa"
        else -> "The Pink Cloud"
    }

    val artistText = when (currentStep) {
        1 -> "Vincent van Gogh"
        2 -> "Leonardo da Vinci"
        else -> "Henri-Edmond Cross"
    }

    val yearText = when (currentStep) {
        1 -> "1889"
        2 -> "1503"
        else -> "1896"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // SECTION A: High-Contrast Gallery Frame Display
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = titleText,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SECTION B: Textured Slate Information Context Card
        Surface(
            color = Color(0xFFECEFF1),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = titleText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    Text(
                        text = artistText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "($yearText)",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // SECTION C: Navigation Direction Click Trigger Row Button Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    currentStep = if (currentStep > 1) currentStep - 1 else totalSteps
                },
                modifier = Modifier.width(135.dp)
            ) {
                Text(text = "Previous")
            }

            Button(
                onClick = {
                    currentStep = if (currentStep < totalSteps) currentStep + 1 else 1
                },
                modifier = Modifier.width(135.dp)
            ) {
                Text(text = "Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtspaceTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            ArtSpaceScreen()
        }
    }
}