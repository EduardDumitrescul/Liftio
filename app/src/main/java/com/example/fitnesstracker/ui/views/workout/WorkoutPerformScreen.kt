package com.example.fitnesstracker.ui.views.workout

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.CenteredAppBar
import com.example.fitnesstracker.ui.components.button.IconButton
import com.example.fitnesstracker.ui.theme.AppTheme
import com.example.fitnesstracker.ui.views.template.edit.EditableExerciseCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutOngoingView(
    viewModel: WorkoutOngoingViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val ongoingWorkout by viewModel.ongoingWorkout.collectAsState()
    val elapsedTime by viewModel.elapsedTime.collectAsState()

    Scaffold(
        topBar = { AppBar(onClick = { /*TODO*/})  },
        containerColor = AppTheme.colors.background
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = AppTheme.dimensions.paddingLarge),
        ) {
            Timer(elapsedTime = elapsedTime)

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(ongoingWorkout.exercisesWithSetsAndMuscles) { exerciseDetailed ->
                    EditableExerciseCard(
                        detailedExercise = exerciseDetailed,
                        onClick = { /*TODO*/ },
                        onRemoveClick = {
                            viewModel.removeExerciseFromWorkout(exerciseDetailed.templateExerciseCrossRefId)
                        },
                        updateSet = { set ->
                            viewModel.updateSet(set)
                        },
                        addSet = {
                            viewModel.addSet(exerciseDetailed.templateExerciseCrossRefId)
                        },
                        removeSet = {
                            viewModel.removeSet(exerciseDetailed.templateExerciseCrossRefId, it)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

//                item {
//                    TextButton(
//                        text = "new exercise",
//                        imageVector = Icons.Rounded.Add,
//                        onClick = onNewExerciseButtonClick
//                    )
//                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    onClick: () -> Unit,
) {
    CenteredAppBar(
        title = "Pull Workout",
        actions = {
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Rounded.CheckCircleOutline,
                contentDescription = "finish workout",
                containerColor = Color.Transparent,
                contentColor = AppTheme.colors.primary,
                size = 40.dp
            )
        }
    )
}