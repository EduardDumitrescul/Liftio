package com.example.fitnesstracker.ui.views.history.exercise.dateWithSets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.data.dto.DateWithSets
import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.ui.theme.AppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// TODO make suffix stand out less (maybe change color, font)

@Composable
fun DateWithSetsColumn(
    state: DateWithSets,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = state.date.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, y")),
            style = AppTheme.typography.caption,
            color = AppTheme.colors.onBackground
        )
        HorizontalDivider(
            color = AppTheme.colors.outline
        )

        state.sets.forEach { set ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Spacer(modifier = Modifier.width(128.dp))
                Text(
                    text = "${set.weight} kg",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.onBackground
                )

                Text(
                    text = "${set.reps} reps",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewDateWithSetsColumn() {
    val state = DateWithSets(
        date = LocalDateTime.now(),
        sets = listOf(
            ExerciseSet(0, 0, 0, 10, 40),
            ExerciseSet(0, 0, 0, 10, 40),
            ExerciseSet(0, 0, 0, 10, 40),
            ExerciseSet(0, 0, 0, 10, 40),
        )
    )
    AppTheme {
        DateWithSetsColumn(
            state = state
        )
    }
}