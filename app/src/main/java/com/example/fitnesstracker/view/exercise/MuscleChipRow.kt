package com.example.fitnesstracker.view.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.view.components.chip.FilledChip
import com.example.fitnesstracker.view.components.chip.OutlinedChip
import com.example.fitnesstracker.view.theme.AppTheme

data class MuscleChipRowModel(
    val primaryMuscle: String,
    val secondaryMuscles: List<String>
)


@Composable
fun MuscleChipRow(
    model: MuscleChipRowModel,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilledChip(label = model.primaryMuscle)
        model.secondaryMuscles.forEach {
            OutlinedChip(label = it)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewMuscleChipRow() {
    val model = MuscleChipRowModel(
        "chest",
        listOf("shoulders", "triceps"),
    )
    AppTheme {
        MuscleChipRow(model = model)
    }
}
