package com.example.fitnesstracker.ui.components.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilledTextField(
    onValueChange: (String) -> Unit,
    placeholderText: String = ""
) {
    val interactionSource = remember { MutableInteractionSource() }
    var text by remember { mutableStateOf("") }

    BasicTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        modifier = Modifier.height(40.dp).fillMaxWidth(),
        cursorBrush = SolidColor(AppTheme.colors.primary),
        enabled = true,
        interactionSource = interactionSource,
        singleLine = true,
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = text,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            placeholder = {
                Text(
                    text = placeholderText,
                    style = AppTheme.typography.body,
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "search",
                    modifier = Modifier.size(AppTheme.dimensions.iconSmall)
                )
            },
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
                focusedContainerColor = AppTheme.colors.container,
            ),
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 0.dp,
                bottom = 0.dp,
            ),
        )
    }
}

@Composable
@Preview
private fun PreviewFilledTextField() {
    AppTheme {
        FilledTextField(
            onValueChange = {
            }
        )
    }
}