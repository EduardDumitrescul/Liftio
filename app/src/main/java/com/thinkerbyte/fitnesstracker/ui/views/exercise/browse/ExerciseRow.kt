package com.thinkerbyte.fitnesstracker.ui.views.exercise.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thinkerbyte.fitnesstracker.data.dto.ExerciseWithMuscles
import com.thinkerbyte.fitnesstracker.data.models.Exercise
import com.thinkerbyte.fitnesstracker.data.models.Muscle
import com.thinkerbyte.fitnesstracker.ui.components.MuscleChipRow
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme
import com.thinkerbyte.fitnesstracker.utils.capitalize

@Composable
fun ExerciseRow(
    model: ExerciseWithMuscles,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EquipmentIcon(model.exercise.equipment)
        Column {
            Text(
                text = model.exercise.name.capitalize(),
                color = AppTheme.colors.onBackground,
                style = AppTheme.typography.headline
            )

            MuscleChipRow(model = model.getMuscleChipRowModel())
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun PreviewExerciseRow() {
    AppTheme {
        ExerciseRow(
            ExerciseWithMuscles(
                exercise = Exercise(
                    id = 1,
                    description = "!231",
                    equipment = "barbell",
                    name = "Bench Press"),
                primaryMuscle = Muscle.default(),
                secondaryMuscles = listOf(),
            )
        )
    }
}