package com.example.fitnesstracker.ui.views.template.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun SetRow(
    exerciseSet: ExerciseSet,
    modifier: Modifier = Modifier,
    style: SetRowStyle = SetRowStyle.NORMAL
) {
    val alpha = if(style == SetRowStyle.DISABLED) 0.5f else 1.0f
    val backgroundColor = if(style == SetRowStyle.HIGHLIGHTED) AppTheme.colors.containerVariant else Color.Transparent

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(backgroundColor)
            .alpha(alpha)
            .then(modifier)
    ) {
        Text(
            text = exerciseSet.index.toString() + '.',
            style = AppTheme.typography.body,
            color = AppTheme.colors.onContainer,
        )

        Text(
            text = exerciseSet.reps.toString() + " reps",
            style = AppTheme.typography.body,
            color = AppTheme.colors.onContainer,
        )

        Text(
            text = exerciseSet.weight.toString() + "kg",
            style = AppTheme.typography.body,
            color = AppTheme.colors.onContainer,
        )
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewSetRow() {
    val exerciseSet = ExerciseSet(1, 1, 1, 10, 20)
    AppTheme {
        Column() {

            SetRow(
                exerciseSet,
                modifier = Modifier.width(160.dp)
            )
            SetRow(
                exerciseSet,
                style = SetRowStyle.DISABLED,
                modifier = Modifier.width(160.dp)
            )
            SetRow(
                exerciseSet,
                style = SetRowStyle.HIGHLIGHTED,
                modifier = Modifier.width(160.dp)
            )
        }
    }
}

enum class SetRowStyle {
    NORMAL,
    HIGHLIGHTED,
    DISABLED
}