package com.example.fitnesstracker.ui.views.template.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.data.dto.ExerciseDetailed
import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.ui.components.MuscleChipRow
import com.example.fitnesstracker.ui.components.MuscleChipRowModel
import com.example.fitnesstracker.ui.components.card.LargeCard
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun ExerciseCard(
    exerciseDetailed: ExerciseDetailed,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
   LargeCard(
       onClick = onClick,
       modifier = modifier,
   ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
        ) {
            Text(
                text = exerciseDetailed.exercise.name,
                style = AppTheme.typography.headline,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )

            MuscleChipRow(
                model = MuscleChipRowModel(
                    exerciseDetailed.primaryMuscle.name,
                    exerciseDetailed.secondaryMuscles.map { it.name }
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Column(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                for(set in exerciseDetailed.sets) {
                    SetRow(
                        exerciseSet = set,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .padding(horizontal = 20.dp),

                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewExerciseCard() {
    AppTheme {
        ExerciseCard(
            exerciseDetailed = ExerciseDetailed.default().copy(
                sets = listOf(
                    ExerciseSet(0, 1, 1, 10, 20),
                    ExerciseSet(0, 1, 2, 10, 20),
                    ExerciseSet(0, 1, 3, 10, 20),
                    ExerciseSet(0, 1, 4, 10, 20),
                    )
            ),
            {}
        )
    }
}