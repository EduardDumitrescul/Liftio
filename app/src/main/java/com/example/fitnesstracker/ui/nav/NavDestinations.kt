package com.example.fitnesstracker.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface NavDestination {
    val icon: ImageVector
    val route: String
    val name: String
}

data object Home: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.Home
    override val route: String
        get() = "home"
    override val name: String
        get() = "Home"
}

data object TemplateView: NavDestination {
    override val icon: ImageVector
        get() = Icons.AutoMirrored.Rounded.List
    override val route: String
        get() = "template/{id}"
    override val name: String
        get() = "template view"

    fun createRoute(id: Int): String {
        return "template/$id"
    }
}

data object TemplateEdit: NavDestination {
    override val icon: ImageVector
        get() = Icons.AutoMirrored.Rounded.List
    override val route: String
        get() = "template/edit/{id}"
    override val name: String
        get() = "template edit"

    fun createRoute(id: Int): String {
        return "template/edit/$id"
    }
}

data object PerformWorkout: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.FitnessCenter
    override val route: String
        get() = "workout/perform/{id}"
    override val name: String
        get() = "perform workout"

    fun createRoute(id: Int): String {
        return "workout/perform/$id"
    }

}

data object Exercises: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.FitnessCenter
    override val route: String
        get() = "exercises"
    override val name: String
        get() = "Exercises"
}

data object SelectExercise: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.CheckCircleOutline
    override val route: String
        get() = "selectExercise"
    override val name: String
        get() = "Select Exercise"
}

data object EditExercise: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.Edit
    override val route: String
        get() = "exercise/{id}"
    override val name: String
        get() = "exercise edit"
    fun createRoute(id: Int): String {
        return "exercise/$id"
    }
}

data object Analysis: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.BarChart
    override val route: String
        get() = "analysis"
    override val name: String
        get() = "Analysis"
}

data object WorkoutHistory: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.History
    override val route: String
        get() = "history"
    override val name: String
        get() = "History"
}

data object ExerciseHistory: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.History
    override val route: String
        get() = "exercise/{id}/history"
    override val name: String
        get() = "exercise history"
    fun createRoute(id: Int): String {
        return "exercise/$id/history"
    }
}

data object Settings: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.Settings
    override val route: String
        get() = "settings"
    override val name: String
        get() = "Settings"

}

val bottomNavBarDestinations = setOf(Home, Exercises, Analysis, WorkoutHistory)


