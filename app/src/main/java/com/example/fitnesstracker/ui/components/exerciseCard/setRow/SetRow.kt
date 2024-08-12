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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.components.SwipeToDeleteContainer
import com.example.fitnesstracker.ui.components.button.TwoButtonRow
import com.example.fitnesstracker.ui.components.textfield.NumberField
import com.example.fitnesstracker.ui.theme.AppTheme
import kotlin.math.min

private const val TAG = "SetRow"

@Composable
fun EditableSetRow(
    state: SetState,
    modifier: Modifier = Modifier,
    options: SetRowOptions = SetRowOptions(),
    onValuesChanged: (SetState) -> Unit,
    removeSet: () -> Unit,
) {
    if(options.canRemoveSet) {
        SwipeToDeleteContainer(onDelete = removeSet) {
            EditableSetRowContent(
                modifier = modifier,
                state = state,
                options = options,
                onValuesChanged = onValuesChanged
            )
        }
    }
    else {
        EditableSetRowContent(
            modifier = modifier,
            state = state,
            options = options,
            onValuesChanged = onValuesChanged
        )
    }
}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun SwipeableSetRow(
//    onSwipe: () -> Unit,
//    state: SetState,
//    modifier: Modifier,
//    options: SetRowOptions = SetRowOptions(),
//    onValuesChanged: (SetState) -> Unit,
//) {
//    val dismissState = rememberSwipeToDismissBoxState(
//        positionalThreshold = {it * 0.15f},
//    )
//    when(dismissState.currentValue) {
//        SwipeToDismissBoxValue.EndToStart -> { onSwipe() }
//        SwipeToDismissBoxValue.StartToEnd -> { onSwipe() }
//        SwipeToDismissBoxValue.Settled -> {}
//    }
//
//    SwipeToDismissBox(
//        state = dismissState,
//        backgroundContent = {
//            DismissBackground()
//        },
//    ) {
//        EditableSetRowContent(
//            modifier,
//            state,
//            options,
//            onValuesChanged
//        )
//    }
//}

@Composable
private fun EditableSetRowContent(
    modifier: Modifier,
    state: SetState,
    options: SetRowOptions,
    onValuesChanged: (SetState) -> Unit
) {
    var isEditing by remember {
        mutableStateOf(false)
    }
    val alpha = if(state.status == SetStatus.TODO) 0.5f else 1.0f

    val backgroundColor = when {
        state.status == SetStatus.ONGOING ->  AppTheme.colors.containerVariant
        isEditing ->  AppTheme.colors.containerVariant
        else -> AppTheme.colors.container
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
            onClick = {
                if (options.canUpdateValues) {
                    isEditing = true
                }
            }
        )

        if (isEditing) {
            EditingArea(
                state,
                onSave = {
                    onValuesChanged(it)
                    isEditing = false
                },
                onCancel = { isEditing = false }
            )
        }
    }
}

@Composable
private fun MainRow(
    state: SetState,
    onClick: () -> Unit,
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        IndexText(state.index)

        RepsText(state.reps)

        WeightText(state.weight)
    }
}

@Composable
private fun WeightText(weight: Int) {
    Text(
        text = "$weight kg",
        style = AppTheme.typography.body,
        modifier = Modifier.defaultMinSize(minWidth = 60.dp),
    )
}

@Composable
private fun RepsText(reps: Int) {
    Text(
        text = "$reps reps",
        style = AppTheme.typography.body,
        modifier = Modifier.defaultMinSize(minWidth = 60.dp),
    )
}

@Composable
private fun IndexText(index: Int) {
    Text(
        text = "$index.",
        style = AppTheme.typography.body,
        color = AppTheme.colors.onContainer,
    )
}

//@Composable
//private fun RemoveButton(onRemoveClicked: () -> Unit) {
//    IconButton(
//        onClick = onRemoveClicked,
//        Modifier.size(24.dp)
//    ) {
//        Icon(
//            imageVector = Icons.Rounded.Remove,
//            contentDescription = "remove set",
//            Modifier.size(20.dp)
//        )
//    }
//}

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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.containerVariant)
            .padding(8.dp)
    ) {

        ValueEditRow(
            value = repsValue,
            text = "reps",
            onValueChange = { repsValue = validateString(it)},
            onIncreaseValue = { repsValue = addToIntString(repsValue, 1) },
            onDecreaseValue = { repsValue = addToIntString(repsValue, -1) },
        )

        ValueEditRow(
            value = weightValue,
            text = "kg",
            onValueChange = { weightValue = validateString(it)},
            onIncreaseValue = { weightValue = addToIntString(weightValue, 1) },
            onDecreaseValue = { weightValue = addToIntString(weightValue, -1) },
        )

        TwoButtonRow(
            primaryButtonText = "save",
            onPrimaryButtonClick = {
                onSave(
                    state.copy(
                        reps = getIntFromString(repsValue),
                        weight = getIntFromString(weightValue)
                    )
                )
            },
            secondaryButtonText = "cancel",
            onSecondaryButtonClick = onCancel,
            modifier = Modifier
                .width(200.dp)
                .height(32.dp)
        )
    }
}

@Composable
private fun ValueEditRow(
    value: String,
    text: String,
    onValueChange: (String) -> Unit,
    onIncreaseValue: () -> Unit,
    onDecreaseValue: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        NumberField(
            value = value,
            onValueChange = onValueChange,
            onIncreaseValue = onIncreaseValue,
            onDecreaseValue = onDecreaseValue,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = text,
            style = AppTheme.typography.body,
            color = AppTheme.colors.onContainerVariant,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        )
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

//@Composable
//fun DismissBackground() {
//    val  color = AppTheme.colors.red.copy(alpha = 0.8f)
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color)
//            .padding(16.dp, 8.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Icon(
//            Icons.Default.Delete,
//            contentDescription = "delete"
//        )
//        Spacer(modifier = Modifier)
//        Icon(
//            Icons.Default.Delete,
//            contentDescription = "delete"
//        )
//    }
//}

@Composable
@Preview(showBackground = true)
fun PreviewEditableSetRow() {
    val state by remember{
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
                removeSet = {}
            )
            EditableSetRow(
                state,
                modifier = Modifier.width(160.dp),
                onValuesChanged = {},
                removeSet = {}
            )
            EditableSetRow(
                state,
                modifier = Modifier.width(160.dp),
                onValuesChanged = {},
                removeSet = {}
            )
        }
    }
}
