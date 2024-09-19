package com.example.fitnesstracker.ui.views.workout.perform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.CenteredAppBar
import com.example.fitnesstracker.ui.components.button.FilledButton
import com.example.fitnesstracker.ui.components.button.IconButton
import com.example.fitnesstracker.ui.components.button.TwoButtonRow
import com.example.fitnesstracker.ui.components.dialog.ConfirmationDialog
import com.example.fitnesstracker.ui.components.exerciseCard.EditableExerciseCard
import com.example.fitnesstracker.ui.components.exerciseCard.ExerciseCardOptions
import com.example.fitnesstracker.ui.components.exerciseCard.Progress
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetRowOptions
import com.example.fitnesstracker.ui.theme.AppTheme
import com.example.fitnesstracker.ui.views.workout.SetEditController
import com.example.fitnesstracker.ui.views.workout.components.AddExerciseButton
import com.example.fitnesstracker.ui.views.workout.components.Timer

// TODO don't let an exercise with no sets be completed
// TODO confirmation on workout finish (if not all exercises have been completed)
// TODO after all exercises completed, display finish workout button instead of complete exercise
// TODO make this persistent (even after going back)

private const val TAG = "WorkoutOngoingView"

@Composable
fun WorkoutOngoingView(
    previouslySelectedExerciseId: Int = 0,
    viewModel: WorkoutPerformViewModel = hiltViewModel(),
    onNewExerciseButtonClick: () -> Unit,
    onViewExerciseHistoryClick: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(previouslySelectedExerciseId) {
        if(previouslySelectedExerciseId > 0) {
            viewModel.addExercise(previouslySelectedExerciseId)
        }
    }

    val ongoingWorkout by viewModel.ongoingWorkout.collectAsState()
    val exerciseEndReached by viewModel.exerciseEndReachedFlow.collectAsState()
    val workoutEndReached by viewModel.workoutEndReachedFlow.collectAsState()
    val setEditController = SetEditController()

    Scaffold(
        topBar = { AppBar(
            title = ongoingWorkout.name,
            workoutEndReached = workoutEndReached,
            navigateBack = navigateBack,
            finishWorkout = {
                viewModel.completeWorkout()
                navigateBack()
            })  },
        bottomBar = {
            BottomBar(
                showCompleteExerciseButton = exerciseEndReached,
                showFinishWorkoutButton = workoutEndReached,
                completeSet = { viewModel.completeSet() },
                completeExercise = { viewModel.completeExercise() },
                completeWorkout = {
                    viewModel.completeWorkout()
                    navigateBack()
                },
                skipSet = { viewModel.skipSet() }
            )
        },
        containerColor = AppTheme.colors.background
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = AppTheme.dimensions.paddingLarge),
        ) {
            Timer(
                elapsedTime = ongoingWorkout.duration,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(ongoingWorkout.exerciseCardStates) { exerciseDetailed ->
                    EditableExerciseCard(
                        state = exerciseDetailed,
                        setEditController = setEditController,
                        options = ExerciseCardOptions().copy(
                            canAddSet = exerciseDetailed.progress == Progress.ONGOING,
                            canRemoveExercise = true,
                            setRowOptions = SetRowOptions().copy(
                                canUpdateValues = true,
                                canRemoveSet = true
                            ),
                            canViewHistory = true,
                        ),
                        onClick = { /*TODO*/ },
                        onRemoveClick = {
                            viewModel.removeExerciseFromWorkout(exerciseDetailed.workoutExerciseCrossRefId)
                        },
                        onHistoryClick = {
                            onViewExerciseHistoryClick(exerciseDetailed.exercise.id)
                        },
                        updateSet = { set ->
                            viewModel.updateSet(set)
                        },
                        addSet = {
                            viewModel.addSet(exerciseDetailed.workoutExerciseCrossRefId)
                        },
                        removeSet = {
                            viewModel.removeSet(it)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    AddExerciseButton(onNewExerciseButtonClick = onNewExerciseButtonClick)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    title: String,
    workoutEndReached: Boolean,
    finishWorkout: () -> Unit,
    navigateBack: () -> Unit,
) {
    CenteredAppBar(
        title = title,
        onNavigationIconClick = navigateBack,
        actions = {
            FinishButton(
                workoutEndReached = workoutEndReached,
                finishWorkout = {finishWorkout()}
            )
        }
    )
}

@Composable
private fun FinishButton(
    workoutEndReached: Boolean,
    finishWorkout: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    IconButton(
        onClick = { if(workoutEndReached) finishWorkout() else showDialog=true },
        modifier = Modifier.size(40.dp),
        imageVector = Icons.Rounded.CheckCircleOutline,
        contentDescription = "finish workout",
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.onBackground,
        size = 40.dp
    )

    if(showDialog) {
        ConfirmationDialog(
            mainText = "End Workout?",
            secondaryText = "You have uncompleted exercises",
            cancelText = "No, continue",
            confirmText = "Finish workout",
            isWarning = true,
            onCancel = {showDialog = false},
            onConfirm = {
                finishWorkout()
                showDialog = false
            },
            onDismissRequest = {showDialog = false},
        )
    }
}

@Composable
private fun BottomBar(
    showCompleteExerciseButton: Boolean,
    showFinishWorkoutButton: Boolean,
    completeSet: () -> Unit,
    completeExercise: () -> Unit,
    completeWorkout: () -> Unit,
    skipSet: () -> Unit,
) {
    if(showFinishWorkoutButton) {
        FilledButton(
            text = "END WORKOUT",
            onClick = completeWorkout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 4.dp)
        )
    }
    else if(showCompleteExerciseButton) {
        FilledButton(
            text = "complete exercise",
            onClick = completeExercise,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 4.dp)
        )
    }
    else {
        TwoButtonRow(
            primaryButtonText = "complete set",
            onPrimaryButtonClick = completeSet,
            secondaryButtonText = "skip",
            onSecondaryButtonClick = skipSet,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 4.dp)
        )
    }
}