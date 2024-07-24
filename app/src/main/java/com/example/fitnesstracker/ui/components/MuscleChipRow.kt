package com.example.fitnesstracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.ui.components.chip.FilledChip
import com.example.fitnesstracker.ui.components.chip.OutlinedChip
import com.example.fitnesstracker.ui.theme.AppTheme

data class MuscleChipRowModel(
    val primaryMuscle: String,
    val secondaryMuscles: List<String>
)


@Composable
fun MuscleChipRow(
    model: MuscleChipRowModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
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
