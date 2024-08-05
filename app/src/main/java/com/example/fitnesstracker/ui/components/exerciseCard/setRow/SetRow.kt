package com.example.fitnesstracker.ui.components.exerciseCard.setRow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun SetRow(
    state: SetState,
    modifier: Modifier = Modifier,
) {
    val alpha = if(state.status == SetStatus.TODO) 0.5f else 1.0f
    val backgroundColor = if(state.status == SetStatus.ONGOING) AppTheme.colors.containerVariant else Color.Transparent

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(backgroundColor)
            .alpha(alpha)
            .then(modifier)
    ) {
        Text(
            text = state.index.toString() + '.',
            style = AppTheme.typography.body,
            color = AppTheme.colors.onContainer,
        )

        Text(
            text = state.reps.toString() + " reps",
            style = AppTheme.typography.body,
            color = AppTheme.colors.onContainer,
        )

        Text(
            text = state.weight.toString() + "kg",
            style = AppTheme.typography.body,
            color = AppTheme.colors.onContainer,
        )
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewSetRow() {
    val exerciseSet = ExerciseSet(1, 1, 1, 10, 20).toSetState()
    AppTheme {
        Column {

            SetRow(
                exerciseSet,
                modifier = Modifier.width(160.dp)
            )
            SetRow(
                exerciseSet,
                modifier = Modifier.width(160.dp)
            )
            SetRow(
                exerciseSet,
                modifier = Modifier.width(160.dp)
            )
        }
    }
}