package com.example.fitnesstracker.ui.components.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun MultiChoiceChipGroupField(
    title: String,
    options: List<String>,
    selectedOptions: Set<String>,
    onSelectionChanged: (Set<String>) -> Unit,
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

        MultiChoiceChipGroup(
            options = options,
            selected = selectedOptions,
            onSelectionChanged = onSelectionChanged
        )
    }
}