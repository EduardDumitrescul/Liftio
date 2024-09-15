package com.example.fitnesstracker.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.Edit
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

object TemplateView: NavDestination {
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

object TemplateEdit: NavDestination {
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

object PerformWorkout: NavDestination {
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

object Exercises: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.FitnessCenter
    override val route: String
        get() = "exercises"
    override val name: String
        get() = "Exercises"
}

object SelectExercise: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.CheckCircleOutline
    override val route: String
        get() = "selectExercise"
    override val name: String
        get() = "Select Exercise"
}

object EditExercise: NavDestination {
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

object Analysis: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.BarChart
    override val route: String
        get() = "analysis"
    override val name: String
        get() = "Analysis"
}

object WorkoutHistory: NavDestination {
    override val icon: ImageVector
        get() = Icons.Rounded.History
    override val route: String
        get() = "history"
    override val name: String
        get() = "History"
}

object ExerciseHistory: NavDestination {
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

val bottomNavBarDestinations = setOf(Home, Exercises, Analysis, WorkoutHistory)


