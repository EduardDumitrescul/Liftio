package com.example.fitnesstracker.ui.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.fitnesstracker.ui.views.exercise.browse.ExerciseBrowseView
import com.example.fitnesstracker.ui.views.exercise.edit.ExerciseEditView
import com.example.fitnesstracker.ui.views.template.browse.TemplateBrowseView
import com.example.fitnesstracker.ui.views.template.detail.TemplateDetailedView

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home.route,
    ) {
        composable(route = Home.route) {
            TemplateBrowseView(
                navigateToTemplateDetailedView = {
                    navController.navigate(TemplateView.createRoute(it))
                }
            )
        }
        composable(route = TemplateView.route) {
            TemplateDetailedView()
        }
        composable(route = Exercises.route) {
            ExerciseBrowseView(
                navigateBack = { navController.navigateUp() },
                navigateToExerciseEditView = {navController.navigate(EditExercise.createRoute(it))}
            )
        }
        composable(route = Stats.route) {
            Text(text = "Stats")
        }
        composable(route = History.route) {
            Text(text = "History")
        }
        composable(route = EditExercise.route) {
            ExerciseEditView(navigateBack = {navController.navigateUp()})
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }


