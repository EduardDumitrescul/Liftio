package com.example.fitnesstracker.ui.components.exerciseCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.components.MuscleChipRow
import com.example.fitnesstracker.ui.components.MuscleChipRowModel
import com.example.fitnesstracker.ui.components.button.IconButton
import com.example.fitnesstracker.ui.components.button.TextButton
import com.example.fitnesstracker.ui.components.card.LargeCard
import com.example.fitnesstracker.ui.components.dialog.ConfirmationDialog
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.EditableSetRow
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetRowOptions
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.example.fitnesstracker.ui.theme.AppTheme
import com.example.fitnesstracker.ui.views.workout.SetEditController

//TODO confirmation after trying to remove an exercise (if it contains sets)
//TODO drag to reorder sets

@Composable
fun EditableExerciseCard(
    state: ExerciseCardState,
    modifier: Modifier = Modifier,
    setEditController: SetEditController = SetEditController(),
    options: ExerciseCardOptions = ExerciseCardOptions(),
    onClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    updateSet: (SetState) -> Unit = {},
    addSet: () -> Unit = {},
    removeSet: (Int) -> Unit = {},
) {
    LargeCard(
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Title(state.exercise.name)

                Row(
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    if(options.canViewHistory) {
                        HistoryButton(onHistoryClick)
                    }
                    if(options.canRemoveExercise) {
                        RemoveButton(
                            removeExercise = onRemoveClick,
                            requiresConfirmation = state.requiresConfirmationToDelete()
                        )
                    }
                }
            }


            MuscleChipRow(
                model = MuscleChipRowModel(
                    state.primaryMuscle.name,
                    state.secondaryMuscles.map { it.name }
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SetRowsColumn(
                    state = state,
                    setEditController = setEditController,
                    options = options,
                    updateSet = updateSet,
                    removeSet = removeSet,
                )

                if(options.canAddSet) {
                    AddSetButton(addSet)
                }
            }
        }
    }
}

@Composable
private fun SetRowsColumn(
    state: ExerciseCardState,
    setEditController: SetEditController = SetEditController(),
    options: ExerciseCardOptions,
    updateSet: (SetState) -> Unit,
    removeSet: (Int) -> Unit,
) {
    for (set in state.sets) {
        key(set.id) {
            EditableSetRow(
                state = set,
                setEditController = setEditController,
                options = options.setRowOptions,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .padding(horizontal = 20.dp),
                onValuesChanged = updateSet,
                removeSet = {
                    removeSet(set.id)
                },
            )
        }
    }
}

@Composable
private fun AddSetButton(addSet: () -> Unit) {
    TextButton(
        text = "new set",
        imageVector = Icons.Rounded.Add,
        onClick = addSet,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
private fun Title(text: String) {
    Text(
        text = text,
        style = AppTheme.typography.headline,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    )
}

@Composable
private fun RemoveButton(
    removeExercise: () -> Unit,
    requiresConfirmation: Boolean = false,
) {
    var showConfirmationDialog by remember { mutableStateOf(false) }
    IconButton(
        onClick = {
            if(requiresConfirmation) {
                showConfirmationDialog = true
            }
            else {
                removeExercise()
            }
        },
        imageVector = Icons.Rounded.Remove,
        contentDescription = "remove exercise",
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.onContainer
    )

    if(showConfirmationDialog) {
        ConfirmationDialog(
            mainText = "Remove Exercise?",
            secondaryText = "This exercise contains some of your progress.",
            confirmText = "Remove it",
            cancelText = "No, keep it",
            onCancel = {showConfirmationDialog = false},
            onConfirm = {
                removeExercise()
                showConfirmationDialog = false
            },
            onDismissRequest = {showConfirmationDialog = false}
        )
    }
}

@Composable
private fun HistoryButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        imageVector = Icons.Rounded.History,
        contentDescription = "view exercise history",
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.onContainer
    )
}

data class ExerciseCardOptions(
    val canRemoveExercise: Boolean = false,
    val canAddSet: Boolean = false,
    val setRowOptions: SetRowOptions = SetRowOptions(),
    val canViewHistory: Boolean = false,
)

@Preview
@Composable
fun PreviewEditableExerciseCard() {
    AppTheme {
        EditableExerciseCard(
            state = ExerciseCardState.default(),
        )
    }
}