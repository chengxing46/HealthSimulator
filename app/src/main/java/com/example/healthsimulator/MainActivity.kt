package com.example.healthsimulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.healthsimulator.ui.navigation.Screen
import com.example.healthsimulator.ui.navigation.bottomNavItems
import com.example.healthsimulator.ui.screens.*
import com.example.healthsimulator.ui.theme.HealthSimulatorTheme
import com.example.healthsimulator.ui.theme.Green700
import com.example.healthsimulator.viewmodel.HealthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthSimulatorTheme {
                HealthSimulatorApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthSimulatorApp() {
    val navController = rememberNavController()
    val viewModel: HealthViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "健康模拟器",
                        color = androidx.compose.ui.graphics.Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Green700
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title
                            )
                        },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(viewModel = viewModel)
            }
            composable(Screen.Steps.route) {
                StepsScreen(viewModel = viewModel)
            }
            composable(Screen.Water.route) {
                WaterScreen(viewModel = viewModel)
            }
            composable(Screen.Sleep.route) {
                SleepScreen(viewModel = viewModel)
            }
            composable(Screen.Meals.route) {
                MealsScreen(viewModel = viewModel)
            }
        }
    }
}