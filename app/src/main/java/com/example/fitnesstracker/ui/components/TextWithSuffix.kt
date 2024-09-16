package com.example.fitnesstracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun TextWithSuffix(
    text: String,
    suffix: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = text,
            style = AppTheme.typography.body,
            color = AppTheme.colors.onBackground
        )
        Text(
            text = suffix,
            style = AppTheme.typography.caption,
            color = AppTheme.colors.onBackground.copy(alpha = 0.8f)
        )
    }
}