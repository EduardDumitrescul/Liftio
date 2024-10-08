package com.thinkerbyte.fitnesstracker.ui.views.workout.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thinkerbyte.fitnesstracker.ui.components.appbar.LargeAppBar
import com.thinkerbyte.fitnesstracker.ui.components.button.TextButton
import com.thinkerbyte.fitnesstracker.ui.components.button.TwoButtonRow
import com.thinkerbyte.fitnesstracker.ui.components.dialog.StringInputDialog
import com.thinkerbyte.fitnesstracker.ui.components.exerciseCard.EditableExerciseCard
import com.thinkerbyte.fitnesstracker.ui.components.exerciseCard.ExerciseCardOptions
import com.thinkerbyte.fitnesstracker.ui.components.exerciseCard.setRow.SetRowOptions
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme
import com.thinkerbyte.fitnesstracker.ui.views.workout.WorkoutState
import com.thinkerbyte.fitnesstracker.ui.views.workout.components.AddExerciseButton
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateEditView(
    previouslySelectedExerciseId: Int = 0,
    viewModel: TemplateEditViewModel = hiltViewModel(),
    onNewExerciseButtonClick: () -> Unit,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(previouslySelectedExerciseId) {
        if(previouslySelectedExerciseId > 0) {
            viewModel.addExercise(previouslySelectedExerciseId)
        }
    }

    val templateWithExercises by viewModel.state.collectAsState()

    var shouldShowNameChangeDialog by remember {
        mutableStateOf(false)
    }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            LargeAppBar(
                title = "Edit - ${templateWithExercises.name}",
                onNavigationIconClick = navigateBack
            )
        },
        bottomBar = {
            if(viewModel.isNewTemplate) {
                NewTemplateBottomBar(viewModel, snackbarHostState, navigateBack)
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = AppTheme.colors.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = AppTheme.dimensions.paddingLarge),
        ) {
            ChangeNameButton(onClick = { shouldShowNameChangeDialog = true })

            ExerciseCardsColumn(
                templateWithExercises,
                viewModel,
                onNewExerciseButtonClick
            )
        }
    }

    if(shouldShowNameChangeDialog) {
        StringInputDialog(
            title = "Choose a name",
            initialValue = templateWithExercises.name,
            onDismissRequest = { shouldShowNameChangeDialog = false },
            onSave = {
                viewModel.updateTemplateName(it)
            })
    }

}

@Composable
private fun NewTemplateBottomBar(
    viewModel: TemplateEditViewModel,
    snackbarHostState: SnackbarHostState,
    navigateBack: () -> Unit
) {
    val scope = rememberCoroutineScope()

    TwoButtonRow(
        primaryButtonText = "Save",
        onPrimaryButtonClick = {
            if (!viewModel.wasNameUpdated) {
                scope.launch { snackbarHostState.showSnackbar("Please set a name for the template") }
            } else {
                navigateBack()
            }
        },
        secondaryButtonText = "Discard",
        onSecondaryButtonClick = {
            navigateBack()
            viewModel.removeTemplate()

        }
    )
}

@Composable
private fun ExerciseCardsColumn(
    templateWithExercises: WorkoutState,
    viewModel: TemplateEditViewModel,
    onNewExerciseButtonClick: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        viewModel.locallyReorderExercises(from.index, to.index )
    }

    LazyColumn(
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            templateWithExercises.exerciseCardStates,
            key = { it.workoutExerciseCrossRefId }
        ) { exerciseCardState ->
            ReorderableItem(
                reorderableLazyListState,
                key = exerciseCardState.workoutExerciseCrossRefId
            ) { isDragging ->
                EditableExerciseCard(
                    state = exerciseCardState,
                    options = ExerciseCardOptions(
                        canRemoveExercise = true,
                        canAddSet = true,
                        setRowOptions = SetRowOptions(
                            canRemoveSet = true,
                            canUpdateValues = true
                        )
                    ),
                    onRemoveClick = { viewModel.removeExerciseFromTemplate(exerciseCardState.workoutExerciseCrossRefId) },
                    updateSet = { set -> viewModel.updateSet(set) },
                    addSet = { viewModel.addSet(exerciseCardState.workoutExerciseCrossRefId) },
                    removeSet = { viewModel.removeSet(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .longPressDraggableHandle(
                            onDragStopped = {
                                viewModel.saveExercisesOrder()
                            }
                        )
                )
            }
        }

        item {
            AddExerciseButton(onNewExerciseButtonClick)
        }
    }
}

@Composable
private fun ChangeNameButton(onClick: () -> Unit) {
    TextButton(
        text = "change name",
        imageVector = Icons.Rounded.Edit,
        onClick = onClick,
        modifier = Modifier.padding(bottom = 32.dp)
    )
}

@Preview
@Composable
fun PreviewTemplateEditView() {
    AppTheme {
        TemplateEditView(
            onNewExerciseButtonClick = {},
            navigateBack = {}
        )
    }
}