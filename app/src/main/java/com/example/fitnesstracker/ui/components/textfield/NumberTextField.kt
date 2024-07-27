package com.example.fitnesstracker.ui.components.textfield

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberTextField(
    text: Int,
    suffix: String,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    singleLine: Boolean = true,
    minLines: Int = 1,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = if(isFocused) text.toString() else text.toString() + suffix,
        onValueChange = {
            val value = if(it == "") 0 else it.toInt()
            onValueChange(value)
        },
        modifier = modifier,
        cursorBrush = SolidColor(AppTheme.colors.primary),
        enabled = true,
        interactionSource = interactionSource,
        singleLine = singleLine,
        minLines = minLines,
        textStyle = AppTheme.typography.body.copy(
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions().copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        )
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = text.toString(),
            innerTextField = innerTextField,
            enabled = true,
            singleLine = singleLine,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            placeholder = {
                Text(
                    text = placeholderText,
                    style = AppTheme.typography.body,
                )
            },
            leadingIcon = leadingIcon,
            shape = AppTheme.shapes.roundedSmallCornerShape,
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,

                focusedLeadingIconColor = AppTheme.colors.primary,
                unfocusedLeadingIconColor = AppTheme.colors.primaryVariant,

                unfocusedPlaceholderColor = AppTheme.colors.primaryVariant,
                focusedPlaceholderColor = Color.Transparent,

                unfocusedContainerColor = AppTheme.colors.container,
                focusedContainerColor = AppTheme.colors.primaryVariant,

                unfocusedTextColor = AppTheme.colors.onContainer,
                focusedTextColor = AppTheme.colors.onPrimaryVariant
            ),
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 0.dp,
                bottom = 0.dp,
                start = 4.dp,
                end = 4.dp,
            ),
        )
    }

    BackHandler {
        focusManager.clearFocus()
        keyboardController?.hide()
        focusManager.clearFocus()
    }
}

@Composable
@Preview
private fun PreviewNumberTextField() {
    AppTheme {
        var value by remember {
            mutableIntStateOf(12)
        }
        NumberTextField(
            text = value,
            suffix = " reps",
            onValueChange = {
                value = it
            }
        )
    }
}