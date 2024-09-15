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
import androidx.compose.runtime.key
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
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.EditableSetRow
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetRowOptions
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun EditableExerciseCard(
    state: ExerciseCardState,
    modifier: Modifier = Modifier,
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
                        RemoveButton(onRemoveClick)
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
                SetRowsColumn(state, options, updateSet, removeSet)

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
    options: ExerciseCardOptions,
    updateSet: (SetState) -> Unit,
    removeSet: (Int) -> Unit
) {
    for (set in state.sets) {
        key(set.id) {
            EditableSetRow(
                state = set,
                options = options.setRowOptions,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .padding(horizontal = 20.dp),
                onValuesChanged = updateSet,
                removeSet = {
                    removeSet(set.id)
                }
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
private fun RemoveButton(onRemoveClick: () -> Unit) {
    IconButton(
        onClick = onRemoveClick,
        imageVector = Icons.Rounded.Remove,
        contentDescription = "remove exercise",
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.onContainer
    )
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