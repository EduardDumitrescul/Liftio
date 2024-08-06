package com.example.fitnesstracker.ui.components.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.components.button.IconButton
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberField(
    value: String,
    onValueChange: (String) -> Unit,
    onIncreaseValue: () -> Unit,
    onDecreaseValue: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DecreaseValueButton(onDecreaseValue)

        TextField(value, onValueChange, modifier, leadingIcon)

        IncreaseValueButton(onIncreaseValue)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    leadingIcon: @Composable() (() -> Unit)?,
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier,
        cursorBrush = SolidColor(AppTheme.colors.primary),
        enabled = true,
        interactionSource = interactionSource,
        singleLine = true,
        textStyle = AppTheme.typography.body.copy(
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions().copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            leadingIcon = leadingIcon,
            shape = AppTheme.shapes.roundedSmallCornerShape,
            colors = numberFieldColors,
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 4.dp,
                bottom = 4.dp,
                start = 4.dp,
                end = 4.dp,
            ),
        )
    }
}

@Composable
private fun DecreaseValueButton(
    onDecreaseValue: () -> Unit,
) {
    IconButton(
        onClick = onDecreaseValue,
        imageVector = Icons.Rounded.Remove,
        contentDescription = "decrease",
        containerColor = numberFieldColors.focusedContainerColor,
        contentColor = numberFieldColors.focusedTextColor,
        modifier = Modifier.size(32.dp)
    )
}

@Composable
private fun IncreaseValueButton(
    onIncreaseValue: () -> Unit,
) {
    IconButton(
        onClick = onIncreaseValue,
        imageVector = Icons.Rounded.Add,
        contentDescription = "increase",
        containerColor = numberFieldColors.focusedContainerColor,
        contentColor = numberFieldColors.focusedTextColor,
        modifier = Modifier.size(32.dp)
    )
}

private val numberFieldColors @Composable get() = TextFieldDefaults.colors(
    disabledIndicatorColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    errorIndicatorColor = Color.Transparent,

    focusedLeadingIconColor = AppTheme.colors.primary,
    unfocusedLeadingIconColor = AppTheme.colors.primaryVariant,

    unfocusedPlaceholderColor = AppTheme.colors.primaryVariant,
    focusedPlaceholderColor = Color.Transparent,

    unfocusedContainerColor = AppTheme.colors.primaryVariant,
    focusedContainerColor = AppTheme.colors.primaryVariant,

    unfocusedTextColor = AppTheme.colors.onPrimaryVariant,
    focusedTextColor = AppTheme.colors.onPrimaryVariant
)

@Composable
@Preview
private fun PreviewNumberTextField() {
    AppTheme {
        var value by remember {
            mutableIntStateOf(12)
        }
        NumberField(
            value = value.toString(),
            onValueChange = {
                value = it.toInt()
            },
            onDecreaseValue = {value -= 1},
            onIncreaseValue = {value += 1}
        )
    }
}