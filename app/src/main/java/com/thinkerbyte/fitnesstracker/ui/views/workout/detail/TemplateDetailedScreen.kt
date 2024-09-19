package com.thinkerbyte.fitnesstracker.ui.views.workout.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thinkerbyte.fitnesstracker.ui.components.appbar.LargeAppBar
import com.thinkerbyte.fitnesstracker.ui.components.button.FilledButton
import com.thinkerbyte.fitnesstracker.ui.components.dialog.ConfirmationDialog
import com.thinkerbyte.fitnesstracker.ui.components.exerciseCard.EditableExerciseCard
import com.thinkerbyte.fitnesstracker.ui.components.exerciseCard.toExerciseCardState
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateDetailedView(
    navigateToTemplateEditView: (Int) -> Unit,
    navigateToOngoingWorkout: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: TemplateDetailedViewModel = hiltViewModel(),
) {
    val templateWithExercises by viewModel.detailedWorkout.collectAsState()
    val sessionPreferences by viewModel.sessionPreferences.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            LargeAppBar(
                title = templateWithExercises.name,
                actions = {
                    RemoveButton(onClick = {
                        navigateBack()
                        viewModel.removeTemplate()
                    })
                    EditButton(onClick = {
                        navigateToTemplateEditView(templateWithExercises.id)
                    })
                },
                onNavigationIconClick = navigateBack
            )
        },
        containerColor = AppTheme.colors.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = AppTheme.dimensions.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                StartTrainingButton(
                    existsOngoingWorkout = sessionPreferences.exists,
                    startWorkout = {
                        coroutineScope.launch {
                            val id = viewModel.createWorkoutFromThisTemplate()
                            navigateToOngoingWorkout(id)
                        }
                    },
                    continueWorkout = {
                        navigateToOngoingWorkout(sessionPreferences.workoutId)
                    }
                )
            }

            items(templateWithExercises.detailedExercises) {
                EditableExerciseCard(
                    state = it.toExerciseCardState(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun StartTrainingButton(
    existsOngoingWorkout: Boolean,
    startWorkout: () -> Unit,
    continueWorkout: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    FilledButton(
        text = "start training",
        onClick = {
            if(existsOngoingWorkout) {
                showDialog = true
            }
            else {
                startWorkout()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    )

    if(showDialog) {
        ConfirmationDialog(
            mainText = "Workout in Progress",
            secondaryText = "Please continue the active workout.",
            cancelText = "No, cancel",
            confirmText = "Go to workout",
            onCancel = {showDialog = false},
            onConfirm = {
                showDialog = false
                continueWorkout()
            },
            onDismissRequest = {
                showDialog = false
            }
        )
    }
}

@Composable
private fun EditButton(
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick,
    ) {
        Icon(Icons.Rounded.Edit, "edit template")
    }
}

@Composable
private fun RemoveButton(
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(Icons.Rounded.Delete, "remove template")
    }
}

@Preview
@Composable
fun PreviewTemplateDetailView() {
    AppTheme {
        TemplateDetailedView({}, {}, {})
    }
}