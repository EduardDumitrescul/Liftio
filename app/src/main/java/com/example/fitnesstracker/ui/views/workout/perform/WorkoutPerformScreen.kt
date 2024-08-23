package com.example.fitnesstracker.ui.views.workout.perform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.CenteredAppBar
import com.example.fitnesstracker.ui.components.button.IconButton
import com.example.fitnesstracker.ui.components.button.TwoButtonRow
import com.example.fitnesstracker.ui.components.exerciseCard.EditableExerciseCard
import com.example.fitnesstracker.ui.components.exerciseCard.ExerciseCardOptions
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetRowOptions
import com.example.fitnesstracker.ui.theme.AppTheme
import com.example.fitnesstracker.ui.views.workout.components.AddExerciseButton
import com.example.fitnesstracker.ui.views.workout.components.Timer

@Composable
fun WorkoutOngoingView(
    previouslySelectedExerciseId: Int = 0,
    viewModel: WorkoutOngoingViewModel = hiltViewModel(),
    onNewExerciseButtonClick: () -> Unit,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(previouslySelectedExerciseId) {
        if(previouslySelectedExerciseId > 0) {
            viewModel.addExercise(previouslySelectedExerciseId)
        }
    }

    val ongoingWorkout by viewModel.ongoingWorkout.collectAsState()
    val elapsedTime by viewModel.elapsedTime.collectAsState()

    Scaffold(
        topBar = { AppBar(
            navigateBack = navigateBack,
            onFinishButtonClick = {
                viewModel.finishWorkout()
                navigateBack()
            })  },
        bottomBar = {
            BottomBar(
                completeSet = { viewModel.completeSet() },
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
                elapsedTime = elapsedTime,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(ongoingWorkout.exerciseCardStates) { exerciseDetailed ->
                    EditableExerciseCard(
                        state = exerciseDetailed,
                        options = ExerciseCardOptions().copy(
                            canAddSet = exerciseDetailed.workoutExerciseCrossRefId == viewModel.currentWorkoutExerciseId,
                            canRemoveExercise = true,
                            setRowOptions = SetRowOptions().copy(
                                canUpdateValues = true,
                                canRemoveSet = true
                            )
                        ),
                        onClick = { /*TODO*/ },
                        onRemoveClick = {
                            viewModel.removeExerciseFromWorkout(exerciseDetailed.workoutExerciseCrossRefId)
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
    onFinishButtonClick: () -> Unit,
    navigateBack: () -> Unit,
) {
    CenteredAppBar(
        title = "Pull Workout",
        onNavigationIconClick = navigateBack,
        actions = {
            FinishButton(onFinishButtonClick)
        }
    )
}

@Composable
private fun FinishButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(40.dp),
        imageVector = Icons.Rounded.CheckCircleOutline,
        contentDescription = "finish workout",
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.onBackground,
        size = 40.dp
    )
}

@Composable
private fun BottomBar(
    completeSet: () -> Unit,
    skipSet: () -> Unit,
) {
    TwoButtonRow(
        primaryButtonText = "complete set",
        onPrimaryButtonClick = completeSet,
        secondaryButtonText = "skip",
        onSecondaryButtonClick = skipSet
    )
}