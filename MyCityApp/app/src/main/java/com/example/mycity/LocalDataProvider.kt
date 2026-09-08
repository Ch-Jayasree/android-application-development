package com.example.mycity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info

object LocalDataProvider {
    // 1. App Categories using standard built-in core icons
    val categories = listOf(
        Category(name = "Coffee Shops", icon = Icons.Default.Coffee),
        Category(name = "Restaurants", icon = Icons.Default.Home),
        Category(name = "Parks", icon = Icons.Default.Info)
    )

    // 2. Specific City Recommendations
    val places = listOf(
        // Coffee Shops Category
        Place(
            id = 1,
            name = "The Daily Grind",
            category = "Coffee Shops",
            description = "A cozy downtown spot known for its artisan espresso, freshly baked croissants, and peaceful study corners.",
            address = "123 Main Street, Downtown",
            icon = Icons.Default.Coffee
        ),
        Place(
            id = 2,
            name = "Bean & Brew",
            category = "Coffee Shops",
            description = "A vibrant neighborhood cafe featuring locally roasted organic coffee blends and a spacious outdoor patio space.",
            address = "456 Oak Avenue, Midtown",
            icon = Icons.Default.Coffee
        ),

        // Restaurants Category
        Place(
            id = 3,
            name = "Bella Italia",
            category = "Restaurants",
            description = "An authentic family-owned trattoria serving hand-tossed brick oven pizzas and rich, classic homemade pasta dishes.",
            address = "789 Pasta Boulevard, West End",
            icon = Icons.Default.Home
        ),
        Place(
            id = 4,
            name = "The Green Bistro",
            category = "Restaurants",
            description = "A modern farm-to-table dining establishment prioritizing wholly organic salads, vegan wraps, and seasonal wellness juices.",
            address = "321 Health Way, Northside",
            icon = Icons.Default.Home
        ),

        // Parks Category
        Place(
            id = 5,
            name = "Central Green Park",
            category = "Parks",
            description = "A massive community landscape featuring scenic running trails, a calm boating lake, and sprawling weekend picnic fields.",
            address = "500 Nature Drive, Center District",
            icon = Icons.Default.Info
        ),
        Place(
            id = 6,
            name = "Sunset Hill Reserve",
            category = "Parks",
            description = "A high-altitude hiking lookout reserve showcasing panoramic panoramic landscape views of the entire city skyline at sunset.",
            address = "900 Ridge Overlook, East Hills",
            icon = Icons.Default.Info
        )
    )
}