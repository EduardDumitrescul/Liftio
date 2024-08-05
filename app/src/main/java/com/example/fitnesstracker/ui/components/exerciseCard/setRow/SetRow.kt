package com.example.fitnesstracker.ui.components.exerciseCard.setRow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.components.button.FilledButton
import com.example.fitnesstracker.ui.components.button.OutlinedButton
import com.example.fitnesstracker.ui.components.textfield.NumberField
import com.example.fitnesstracker.ui.theme.AppTheme
import kotlin.math.min

@Composable
fun EditableSetRow(
    state: SetState,
    options: SetRowOptions = SetRowOptions(),
    onValuesChanged: (SetState) -> Unit,
    onRemoveClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isEditing by remember {
        mutableStateOf(false)
    }
    val alpha = if(state.status == SetStatus.TODO) 0.5f else 1.0f

    val backgroundColor = when {
        state.status == SetStatus.ONGOING ->  AppTheme.colors.containerVariant
        isEditing ->  AppTheme.colors.containerVariant
        else -> Color.Transparent
    }

    Column(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .alpha(alpha)
            .then(modifier),
    ) {
        MainRow(
            state = state,
            options = options,
            onRemoveClicked = onRemoveClicked,
            onClick = {
                if(options.canUpdateValues) {
                    isEditing = true
                }
            }
        )

        if(isEditing) {
            EditingArea(
                state,
                onSave = {
                    onValuesChanged(it)
                    isEditing = false
                 },
                onCancel = {isEditing = false}
            )
        }
    }
}

@Composable
private fun MainRow(
    state: SetState,
    options: SetRowOptions,
    onRemoveClicked: () -> Unit,
    onClick: () -> Unit,
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            text = state.index.toString() + '.',
            style = AppTheme.typography.body,
            color = AppTheme.colors.onContainer,
        )
        Text(
            text = state.reps.toString() + " reps",
            style = AppTheme.typography.body,
            modifier = Modifier.defaultMinSize(minWidth = 60.dp),
        )
        Text(
            text = state.weight.toString() + " kg",
            style = AppTheme.typography.body,
            modifier = Modifier.defaultMinSize(minWidth = 60.dp),
        )
        if(options.canRemoveSet) {
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
}

data class SetRowOptions(
    val canRemoveSet: Boolean = false,
    val canUpdateValues: Boolean = false,
)

@Composable
private fun EditingArea(
    state: SetState,
    onSave: (SetState) -> Unit,
    onCancel: () -> Unit,
) {
    var repsValue by remember {
        mutableStateOf(state.reps.toString())
    }

    var weightValue by remember {
        mutableStateOf(state.weight.toString())
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.containerVariant)
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            NumberField(
                value = repsValue,
                onValueChange = {
                    repsValue = validateString(it).toString()
                },
                onIncreaseValue = {
                    repsValue = addToIntString(repsValue, 1)
                },
                onDecreaseValue = {
                    repsValue = addToIntString(repsValue, -1)
                },
                modifier = Modifier.width(120.dp)
            )
            Text(
                text = "reps",
                style = AppTheme.typography.body,
                color = AppTheme.colors.onContainerVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            NumberField(
                value = weightValue,
                onValueChange = {
                    weightValue = validateString(it)
                },
                onIncreaseValue = {
                    weightValue = addToIntString(weightValue, 1)
                                  },
                onDecreaseValue = {
                   weightValue = addToIntString(weightValue, -1)
                                  },
                modifier = Modifier.width(120.dp)
            )
            Text(
                text = "kg",
                style = AppTheme.typography.body,
                color = AppTheme.colors.onContainerVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            OutlinedButton(
                text = "cancel",
                onClick = onCancel,
                modifier = Modifier
                    .height(32.dp)
                    .width(96.dp)
            )

            FilledButton(
                text = "save",
                onClick = {
                    onSave(
                        state.copy(
                            reps = getIntFromString(repsValue),
                            weight = getIntFromString(weightValue)
                        )
                    )
                },
                modifier = Modifier
                    .height(32.dp)
                    .width(96.dp)
            )
        }
    }
}

private fun validateString(value: String): String {
    var temp: String = value
        .filter {it.isDigit()}
        .trimStart('0')
    temp = temp.substring(0, min(5, temp.length))
    return temp
}

private fun getIntFromString(value: String): Int {
    val temp = validateString(value)
    if(temp == "") {
        return 0
    }
    return temp.toInt()
}

private fun addToIntString(value: String, toAdd: Int): String {
    var int = getIntFromString(value)
    int += toAdd
    if(int < 0) {
        int = 0
    }
    return int.toString()
}

@Composable
@Preview(showBackground = true)
fun PreviewEditableSetRow() {
    var state by remember{
        mutableStateOf(
            SetState(
                1, 1, 1, 10, 20,
                SetStatus.DONE
            )
        )
    }
    AppTheme {
        Column {

            EditableSetRow(
                state,
                modifier = Modifier.width(160.dp),
                onValuesChanged = {},
                onRemoveClicked = {}
            )
            EditableSetRow(
                state,
                modifier = Modifier.width(160.dp),
                onValuesChanged = {},
                onRemoveClicked = {}
            )
            EditableSetRow(
                state,
                modifier = Modifier.width(160.dp),
                onValuesChanged = {},
                onRemoveClicked = {}
            )
        }
    }
}
