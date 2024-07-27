package com.example.fitnesstracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitnesstracker.ui.components.button.FilledButton
import com.example.fitnesstracker.ui.components.button.OutlinedButton
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun TwoButtonBottomBar(
    primaryButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    secondaryButtonText: String,
    onSecondaryButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.dimensions.paddingNormal)
            .padding(horizontal = AppTheme.dimensions.paddingLarge),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingLarge)
    ) {
        OutlinedButton(
            text = secondaryButtonText,
            onClick = onSecondaryButtonClick,
            modifier = Modifier.weight(0.5f)
        )
        FilledButton(
            text = primaryButtonText,
            onClick = onPrimaryButtonClick,
            modifier = Modifier.weight(0.5f)
        )
    }
}