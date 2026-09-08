package com.example.flightsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flightsearch.ui.theme.FlightSearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightSearchTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { FlightSearchTopAppBar() }
                ) { innerPadding ->
                    FlightSearchScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Airport(
    val code: String,
    val name: String,
    val city: String
)

data class FlightRowItem(
    val id: Int,
    val departureCode: String,
    val arrivalCode: String,
    var isFavorite: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Flight Search Engine", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun FlightSearchScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedDepartureCode by remember { mutableStateOf<String?>(null) }

    val airportsDatabase = remember {
        listOf(
            Airport("DEL", "Indira Gandhi International", "New Delhi"),
            Airport("BOM", "Chhatrapati Shivaji Maharaj", "Mumbai"),
            Airport("BLR", "Kempegowda International", "Bengaluru"),
            Airport("HYD", "Rajiv Gandhi International", "Hyderabad"),
            Airport("MAA", "Chennai International", "Chennai")
        )
    }

    val favoriteFlights = remember {
        mutableStateListOf(
            FlightRowItem(1, "DEL", "BOM", true),
            FlightRowItem(2, "BLR", "HYD", true)
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                selectedDepartureCode = null // Clear locked flights if user rewrites query strings
            },
            placeholder = { Text("Enter airport name or code...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        selectedDepartureCode = null
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear text")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        if (searchQuery.isEmpty()) {
            Text(
                text = "Favorite Route Caches",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(favoriteFlights) { flight ->
                    FlightCard(
                        departure = flight.departureCode,
                        arrival = flight.arrivalCode,
                        isFav = flight.isFavorite,
                        onFavToggle = { flight.isFavorite = !flight.isFavorite }
                    )
                }
            }
        } else if (selectedDepartureCode != null) {
            // VIEW MODE: Locks in selected departure hub and builds terminal connections immediately!
            Text(
                text = "Flights departing from $selectedDepartureCode",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            val routes = airportsDatabase
                .filter { it.code != selectedDepartureCode }
                .map { FlightRowItem(it.code.hashCode(), selectedDepartureCode!!, it.code) }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(routes) { route ->
                    FlightCard(
                        departure = route.departureCode,
                        arrival = route.arrivalCode,
                        isFav = false,
                        onFavToggle = {}
                    )
                }
            }
        } else {
            val matchingAirports = airportsDatabase.filter {
                it.code.contains(searchQuery, ignoreCase = true) ||
                        it.name.contains(searchQuery, ignoreCase = true) ||
                        it.city.contains(searchQuery, ignoreCase = true)
            }

            Text(
                text = "Matching Terminal Connections",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(matchingAirports) { airport ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                searchQuery = airport.code
                                selectedDepartureCode = airport.code // Lock terminal parameter index target row on tap clicks
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = "${airport.city} (${airport.code})", fontWeight = FontWeight.Bold)
                                Text(text = airport.name, fontSize = 13.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FlightCard(departure: String, arrival: String, isFav: Boolean, onFavToggle: () -> Unit) {
    var favoriteState by remember { mutableStateOf(isFav) }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "DEPART", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = departure, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "ARRIVE", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = arrival, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color.Black)
                }
            }

            IconButton(onClick = {
                favoriteState = !favoriteState
                onFavToggle()
            }) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Toggle favorite status",
                    tint = if (favoriteState) Color(0xFFFFB300) else Color(0xFFE2E8F0)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FlightSearchPreview() {
    FlightSearchTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            FlightSearchScreen()
        }
    }
}