package com.example.mycity

import androidx.compose.ui.graphics.vector.ImageVector

data class Place(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val address: String,
    val icon: ImageVector
)