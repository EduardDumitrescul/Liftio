package com.example.fitnesstracker.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface NavDestination {
    val icon: ImageVector
    val route: String
    val name: String
}

object Home: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.Home
    override val route: String
        get() = "home"
    override val name: String
        get() = "Home"
}

object Exercises: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.FitnessCenter
    override val route: String
        get() = "exercises"
    override val name: String
        get() = "Exercises"
}

object Stats: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.BarChart
    override val route: String
        get() = "stats"
    override val name: String
        get() = "Stats"
}

object History: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.History
    override val route: String
        get() = "history"
    override val name: String
        get() = "History"
}

val bottomNavBarDestinations = setOf(Home, Exercises, Stats, History)


