package com.example.fitnesstracker.ui.components.textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun StringValueEditField(
    title: String,
    initialFieldValue: String,
    onValueChanged: (String) -> Unit,
    placeholderText: String,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
        modifier = Modifier.padding(vertical = AppTheme.dimensions.paddingNormal)
    ) {
        Text(
            text = title,
            style = AppTheme.typography.caption,
            color = AppTheme.colors.onBackground
        )
        FilledTextField(
            text = initialFieldValue,
            onValueChange = onValueChanged,
            placeholderText = placeholderText,
            singleLine = singleLine,
            minLines = minLines,
        )
    }
}