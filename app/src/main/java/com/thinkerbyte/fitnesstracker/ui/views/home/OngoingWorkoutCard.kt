package com.thinkerbyte.fitnesstracker.ui.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thinkerbyte.fitnesstracker.ui.components.card.LargeCard
import com.thinkerbyte.fitnesstracker.ui.components.card.LargeCardDefaults
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme
import com.thinkerbyte.fitnesstracker.ui.theme.AppThemeNoViewModel

@Composable
fun OngoingWorkoutCard(
    state: OngoingWorkoutState,
    onClick: () -> Unit,
) {
    LargeCard(
        onClick = onClick,
        colors = LargeCardDefaults.colors.copy(
            contentColor = AppTheme.colors.onPrimary,
            containerColor = AppTheme.colors.primary
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = state.name,
                style = AppTheme.typography.headline,
            )

            Text(
                text = formatDuration(state.durationInSeconds),
                style = AppTheme.typography.title
            )
        }

    }
}

private fun formatDuration(value: Long): String {
    val hours = value / 3600
    val minutes = value / 60 % 60
    val seconds = value % 60

    var string = ""
    if(hours > 0)
        string += "${hours}h "

    if(minutes > 0)
        string += "${minutes}m "

    string += "${seconds}s"

    return string
}

@Composable
@Preview
private fun PreviewOngoingWorkoutCard() {
    val state = OngoingWorkoutState(
        id = 0,
        exists = true,
        name = "Pull Workout",
        durationInSeconds = 2590,
    )
    AppThemeNoViewModel {
        OngoingWorkoutCard(
            state = state,
            onClick = {}
        )
    }
}