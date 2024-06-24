package com.example.fitnesstracker.ui.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun ExerciseRow(
    model: ExerciseRowModel,
) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EquipmentIcon(model.equipmentType)
        Column {
            Text(
                text = model.exerciseName,
                color = AppTheme.colors.onBackground,
                style = AppTheme.typography.headline
            )

            MuscleChipRow(model = model.getMuscleChipRowModel())
        }
    }
}

data class ExerciseRowModel(
    val equipmentType: EquipmentType,
    val exerciseName: String,
    val primaryMuscle: String,
    val secondaryMuscles: List<String>
) {
    fun getMuscleChipRowModel(): MuscleChipRowModel {
        return MuscleChipRowModel(
            primaryMuscle = primaryMuscle,
            secondaryMuscles = secondaryMuscles,
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun PreviewExerciseRow() {
    AppTheme {
        ExerciseRow(
            ExerciseRowModel(
                equipmentType = EquipmentType.Barbell,
                exerciseName = "Bench Press",
                primaryMuscle = "chest",
                secondaryMuscles = listOf("shoulders", "triceps"),
            )
        )
    }
}