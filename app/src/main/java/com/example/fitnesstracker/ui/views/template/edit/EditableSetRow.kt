package com.example.fitnesstracker.ui.views.template.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.ui.components.textfield.NumberTextField
import com.example.fitnesstracker.ui.theme.AppTheme
import com.example.fitnesstracker.ui.views.template.detail.SetRowStyle

@Composable
fun EditableSetRow(
    exerciseSet: ExerciseSet,
    onValuesChanged: (ExerciseSet) -> Unit,
    onRemoveClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(Color.Transparent)
            .then(modifier)
    ) {
        Text(
            text = exerciseSet.index.toString() + '.',
            style = AppTheme.typography.body,
            color = AppTheme.colors.onContainer,
        )
        NumberTextField(
            text = exerciseSet.reps,
            onValueChange = {
                onValuesChanged(exerciseSet.copy(
                    reps = it
                ))
            },
            modifier = Modifier.width(60.dp),
        )
        NumberTextField(
            text = exerciseSet.weight,
            onValueChange = {
                onValuesChanged(exerciseSet.copy(
                    weight = it
                ))
            },
            modifier = Modifier.width(60.dp),
        )

        IconButton(
            onClick = onRemoveClicked,
            Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Remove,
                contentDescription = "remove set",
                Modifier.size(20.dp)
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewEditableSetRow() {
    var exerciseSet by remember{mutableStateOf( ExerciseSet(1, 1, 1, 10, 20))}
    AppTheme {
        Column() {

            EditableSetRow(
                exerciseSet,
                modifier = Modifier.width(160.dp),
                onValuesChanged = {exerciseSet = it},
                onRemoveClicked = {}
            )
            EditableSetRow(
                exerciseSet,
                modifier = Modifier.width(160.dp),
                onValuesChanged = {exerciseSet = it},
                onRemoveClicked = {}
            )
            EditableSetRow(
                exerciseSet,
                modifier = Modifier.width(160.dp),
                onValuesChanged = {exerciseSet = it},
                onRemoveClicked = {}
            )
        }
    }
}
