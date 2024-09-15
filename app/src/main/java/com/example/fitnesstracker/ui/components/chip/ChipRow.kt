package com.example.fitnesstracker.ui.components.chip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.ui.theme.AppTheme


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SingleChoiceChipGroup(
    options: List<String>,
    onSelectionChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    selected: String = "",
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
        modifier = modifier,
    ) {

        for(option in options) {
            if(option == selected) {
                FilledChip(
                    label = option
                )
            }
            else {
                OutlinedChip(
                    label = option,
                    modifier = Modifier.clickable {
                        onSelectionChanged(option)
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MultiChoiceChipGroup(
    options: List<String>,
    selected: Set<String> = emptySet(),
    onSelectionChanged: (Set<String>) -> Unit
) {

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal)
    ) {

        for(option in options) {
            if(option in selected) {
                FilledChip(
                    label = option,
                    modifier = Modifier.clickable {
                        onSelectionChanged(selected.minus(option))
                    }
                )
            }
            else {
                OutlinedChip(
                    label = option,
                    modifier = Modifier.clickable {
                        onSelectionChanged(selected.plus(option))
                    }
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewChipRow() {
    AppTheme {
        Column {
            SingleChoiceChipGroup(
                options = listOf("Chest", "Shoulders", "Triceps", "Biceps"),
                onSelectionChanged = {},
            )

            MultiChoiceChipGroup(
                options = listOf("Chest", "Shoulders", "Triceps", "Biceps"),
                onSelectionChanged = {},
            )
        }
    }
}