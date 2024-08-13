package com.example.fitnesstracker.ui.components.workoutCard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.R
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun WorkoutEntryCard(
    state: WorkoutEntryCardState,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = { /*TODO*/ },
        modifier = modifier,
        shape = AppTheme.shapes.roundedNormalCornerShape,
        colors = colors,
        border = border
    ) {
        TopRow(
            name = state.name,
            date = state.date,
            duration = state.duration,
            weight = state.weightMoved
        )
        ExerciseListDescription(
            exercises = state.exerciseSummaries
        )
    }
}

@Composable
private fun TopRow(
    name: String,
    date: String,
    duration: String,
    weight: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            WorkoutName(name)
            WorkoutDate(date)
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Statistic(
                imageVector = Icons.Rounded.Timer,
                text = duration
            )
            Statistic(
                painter = painterResource(id = R.drawable.weight_24px),
                text = weight
            )
        }
    }
}

@Composable
private fun WorkoutName(
    name: String
) {
    Text(
        text = name,
        style = AppTheme.typography.headline
    )
}

@Composable
private fun WorkoutDate(
    date: String
) {
    Text(
        text = date,
        style = AppTheme.typography.body
    )
}

@Composable
private fun Statistic(
    imageVector: ImageVector,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = imageVector,
            contentDescription = text
        )
        Text(
            text = text,
            style = AppTheme.typography.caption
        )
    }
}

@Composable
private fun Statistic(
    painter: Painter,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painter,
            contentDescription = text
        )
        Text(
            text = text,
            style = AppTheme.typography.caption
        )
    }
}


@Composable
private fun ExerciseListDescription(
    exercises: List<ExerciseEntrySummary>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Exercise",
                style = AppTheme.typography.caption
            )
            Text(
                text = "Best Effort",
                style = AppTheme.typography.caption
            )
        }

        for(exercise in exercises) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = exercise.name,
                    style = AppTheme.typography.body
                )
                Text(
                    text = exercise.effort,
                    style = AppTheme.typography.body
                )
            }
        }
    }
}


private val colors @Composable get() = CardDefaults.outlinedCardColors(
    containerColor = AppTheme.colors.background,
    contentColor = AppTheme.colors.onBackground
)

private val border @Composable get() = BorderStroke(AppTheme.dimensions.border, AppTheme.colors.outline)

@Preview(widthDp = 412, heightDp = 260, showBackground = true)
@Composable
fun PreviewWorkoutCard() {
    AppTheme {
        WorkoutEntryCard(
            state = WorkoutEntryCardState.default(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}