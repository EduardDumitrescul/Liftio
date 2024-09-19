package com.thinkerbyte.fitnesstracker.ui.views.history.exercise.dateWithSets

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
import com.thinkerbyte.fitnesstracker.data.dto.DateWithSets
import com.thinkerbyte.fitnesstracker.data.models.ExerciseSet
import com.thinkerbyte.fitnesstracker.ui.components.TextWithSuffix
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
                TextWithSuffix(
                    text = set.weight.toString(),
                    suffix = "kg"
                )
                TextWithSuffix(
                    text = set.reps.toString(),
                    suffix = "reps"
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