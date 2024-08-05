package com.example.fitnesstracker.ui.components.exerciseCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                Text(
                    text = state.exercise.name,
                    style = AppTheme.typography.headline,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                )

                if(options.canRemoveExercise) {
                    IconButton(
                        onClick = onRemoveClick,
                        imageVector = Icons.Rounded.DeleteOutline,
                        contentDescription = "remove exercise",
                        containerColor = Color.Transparent,
                        contentColor = AppTheme.colors.red
                    )
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
                for(set in state.sets) {
                    EditableSetRow(
                        state = set,
                        options = options.setRowOptions,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .padding(horizontal = 20.dp),
                        onValuesChanged = updateSet,
                        onRemoveClicked = {
                            removeSet(set.id)
                        }
                    )
                }

                if(options.canAddSet) {
                    TextButton(
                        text = "new set",
                        imageVector = Icons.Rounded.Add,
                        onClick = addSet,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

data class ExerciseCardOptions(
    val canRemoveExercise: Boolean = false,
    val canAddSet: Boolean = false,
    val setRowOptions: SetRowOptions = SetRowOptions(),
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