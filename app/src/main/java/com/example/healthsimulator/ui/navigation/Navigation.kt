package com.example.healthsimulator.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Dashboard : Screen("dashboard", "仪表盘", Icons.Default.Home)
    data object Steps : Screen("steps", "步数", Icons.Default.DirectionsWalk)
    data object Water : Screen("water", "水分", Icons.Default.WaterDrop)
    data object Sleep : Screen("sleep", "睡眠", Icons.Default.Bedtime)
    data object Meals : Screen("meals", "饮食", Icons.Default.Restaurant)
}

val bottomNavItems = listOf(
    Screen.Dashboard,
    Screen.Steps,
    Screen.Water,
    Screen.Sleep,
    Screen.Meals
)