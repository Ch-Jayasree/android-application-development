package com.example.mycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycity.ui.theme.MyCityAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCityAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyCityApp()
                }
            }
        }
    }
}

// State Screen Tracking States
enum class MyCityScreen {
    Categories,
    Places,
    Details
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityApp() {
    // 1. STATE HOISTING: Retain active navigation targets & indexes
    var currentScreen by remember { mutableStateOf(MyCityScreen.Categories) }
    var selectedCategory by remember { mutableStateOf("Coffee Shops") }
    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    // Intercept hardware or system software back clicks gracefully
    BackHandler(enabled = currentScreen != MyCityScreen.Categories) {
        currentScreen = if (currentScreen == MyCityScreen.Details) MyCityScreen.Places else MyCityScreen.Categories
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentScreen) {
                            MyCityScreen.Categories -> "My City Guide"
                            MyCityScreen.Places -> selectedCategory
                            MyCityScreen.Details -> selectedPlace?.name ?: "Details"
                        },
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    if (currentScreen != MyCityScreen.Categories) {
                        IconButton(onClick = {
                            currentScreen = if (currentScreen == MyCityScreen.Details) MyCityScreen.Places else MyCityScreen.Categories
                        }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // 2. VIEW MULTIPLEXING ROTATION INTERFACES
            when (currentScreen) {
                MyCityScreen.Categories -> {
                    CategoryListScreen(
                        categories = LocalDataProvider.categories,
                        onCategoryClick = { catName ->
                            selectedCategory = catName
                            currentScreen = MyCityScreen.Places
                        }
                    )
                }
                MyCityScreen.Places -> {
                    val filteredPlaces = LocalDataProvider.places.filter { it.category == selectedCategory }
                    PlacesListScreen(
                        places = filteredPlaces,
                        onPlaceClick = { place ->
                            selectedPlace = place
                            currentScreen = MyCityScreen.Details
                        }
                    )
                }
                MyCityScreen.Details -> {
                    selectedPlace?.let { place ->
                        PlaceDetailScreen(place = place)
                    }
                }
            }
        }
    }
}

// ======================== SUB-VIEW 1: CATEGORY LIST ========================
@Composable
fun CategoryListScreen(categories: List<Category>, onCategoryClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth().clickable { onCategoryClick(category.name) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = category.name,
                        modifier = Modifier.size(36.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = category.name, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

// ======================== SUB-VIEW 2: PLACES LIST ========================
@Composable
fun PlacesListScreen(places: List<Place>, onPlaceClick: (Place) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(places) { place ->
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth().clickable { onPlaceClick(place) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = place.icon,
                        contentDescription = place.name,
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = place.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(text = place.address, fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

// ======================== SUB-VIEW 3: DETAIL CANVAS ========================
@Composable
fun PlaceDetailScreen(place: Place) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = place.icon,
                contentDescription = place.name,
                modifier = Modifier.size(56.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Location Address", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(text = place.address, fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "About This Place", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(text = place.description, fontSize = 16.sp, lineHeight = 22.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyCityPreview() {
    MyCityAppTheme {
        MyCityApp()
    }
}