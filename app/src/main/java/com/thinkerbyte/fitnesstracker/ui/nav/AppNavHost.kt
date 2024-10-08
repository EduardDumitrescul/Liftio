package com.thinkerbyte.fitnesstracker.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.thinkerbyte.fitnesstracker.ui.views.analysis.AnalysisScreen
import com.thinkerbyte.fitnesstracker.ui.views.exercise.browse.ExerciseBrowseView
import com.thinkerbyte.fitnesstracker.ui.views.exercise.edit.ExerciseEditView
import com.thinkerbyte.fitnesstracker.ui.views.history.WorkoutHistoryScreen
import com.thinkerbyte.fitnesstracker.ui.views.history.exercise.ExerciseHistoryScreen
import com.thinkerbyte.fitnesstracker.ui.views.settings.SettingsScreen
import com.thinkerbyte.fitnesstracker.ui.views.home.TemplateBrowseView
import com.thinkerbyte.fitnesstracker.ui.views.workout.detail.TemplateDetailedView
import com.thinkerbyte.fitnesstracker.ui.views.workout.edit.TemplateEditView
import com.thinkerbyte.fitnesstracker.ui.views.workout.perform.WorkoutOngoingView

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
                },
                navigateToSettings = {
                    navController.navigate(Settings.route)
                },
                navigateToTemplateEditView = {
                    navController.navigate(TemplateEdit.createRoute(0))
                },
                navigateToOngoingWorkout = {
                    navController.navigateSingleTopTo(PerformWorkout.createRoute(it))
                }
            )
        }
        composable(route = TemplateView.route) {
            TemplateDetailedView(
                navigateToTemplateEditView = {navController.navigate(TemplateEdit.createRoute(it))},
                navigateBack = { navController.navigateUp() },
                navigateToOngoingWorkout = { navController.navigateSingleTopTo(PerformWorkout.createRoute(it))}
            )
        }
        composable(route = TemplateEdit.route) { navBackStack ->
            val selectedExerciseId = navBackStack.savedStateHandle.getStateFlow("selectedExerciseId", 0).collectAsState().value
            navBackStack.savedStateHandle.remove<Int>("selectedExerciseId")
            TemplateEditView(
                previouslySelectedExerciseId = selectedExerciseId,
                onNewExerciseButtonClick = {navController.navigate(SelectExercise.route)},
                navigateBack = {navController.navigateUp()}
            )
        }

        composable(route = PerformWorkout.route) { navBackStack ->
            val selectedExerciseId = navBackStack.savedStateHandle.getStateFlow("selectedExerciseId", 0).collectAsState().value
            navBackStack.savedStateHandle.remove<Int>("selectedExerciseId")
            WorkoutOngoingView(
                previouslySelectedExerciseId = selectedExerciseId,
                onNewExerciseButtonClick = {navController.navigate(SelectExercise.route)},
                onViewExerciseHistoryClick = {navController.navigate(ExerciseHistory.createRoute(it))},
                navigateBack = {navController.navigateUp()}
            )
        }

        composable(route = SelectExercise.route) {
            ExerciseBrowseView(
                onExerciseClick = { exerciseId ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedExerciseId", exerciseId)
                    navController.popBackStack()
                },
                onActionClick = { navController.navigate(EditExercise.createRoute(0)) })
        }
        composable(route = Exercises.route) {
            ExerciseBrowseView(
                onExerciseClick = {navController.navigate(ExerciseHistory.createRoute(it))},
                onActionClick = {navController.navigate(EditExercise.createRoute(0))}
            )
        }
        composable(route = Analysis.route) {
            AnalysisScreen()
        }
        composable(route = WorkoutHistory.route) {
            WorkoutHistoryScreen()
        }
        composable(route = EditExercise.route) {
            ExerciseEditView(navigateBack = {navController.navigateUp()})
        }
        composable(route = ExerciseHistory.route) {
            ExerciseHistoryScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = Settings.route) {
            SettingsScreen(
                navigateBack = {navController.navigateUp()}
            )
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


