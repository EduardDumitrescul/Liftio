package com.example.fitnesstracker.ui.views.workout.browse

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.data.dto.WorkoutSummary
import com.example.fitnesstracker.ui.components.card.LargeCard
import com.example.fitnesstracker.ui.components.chip.FilledChip
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun WorkoutCard(
    workout: WorkoutSummary,
    onClick: () -> Unit = {},
) {
    LargeCard(onClick = onClick){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal)
        ) {
            Title(workout.name)

            HorizontalDivider(color = AppTheme.colors.container)

            MusclesRow(workout.workedMuscles)

            ExerciseList(workout.exerciseList)
        }

    }
}

@Composable
private fun ExerciseList(exerciseList: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingExtraSmall)
    ) {
        for (exercise in exerciseList) {
            Text(
                text = exercise,
                style = AppTheme.typography.body,
                color = AppTheme.colors.onContainer.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun MusclesRow(workedMuscles: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
    ) {
        for (muscle in workedMuscles) {
            FilledChip(label = muscle)
        }
    }
}

@Composable
private fun Title(text: String) {
    Text(
        text = text,
        style = AppTheme.typography.headline
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = Color.GRAY.toLong(), widthDp = 300)
private fun PreviewWorkoutCard() {
    AppTheme {
        WorkoutCard(
            workout = WorkoutSummary(
                id = 0,
                name = "Push Workout",
                workedMuscles = listOf("chest", "shoulders", "triceps"),
                exerciseList = listOf(
                    "3 x bench press",
                    "4 x shoulder press",
                    "3 x triceps pushdown",
                    "2 x incline press",
                    "5 x lateral raise")
            )
        )
    }
}