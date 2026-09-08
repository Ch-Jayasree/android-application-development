package com.example.thirtydaysapp

import androidx.annotation.DrawableRes

data class DayTip(
    val dayNumber: Int,
    val title: String,
    val description: String,
    @DrawableRes val imageResId: Int
)