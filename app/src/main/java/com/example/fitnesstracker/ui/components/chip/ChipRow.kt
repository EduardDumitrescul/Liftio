package com.example.fitnesstracker.ui.components.chip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.ui.theme.AppTheme


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SingleChoiceChipGroup(
    options: List<String>,
    onSelectionChanged: (String) -> Unit,
) {
    var selectedOption by remember {
        mutableStateOf("")
    }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal)
    ) {

        for(option in options) {
            if(option == selectedOption) {
                FilledChip(
                    label = option
                )
            }
            else {
                OutlinedChip(
                    label = option,
                    modifier = Modifier.clickable {
                        selectedOption = option
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
    onSelectionChanged: (Set<String>) -> Unit
) {
    var selectedOptions by remember {
        mutableStateOf(setOf<String>())
    }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal)
    ) {

        for(option in options) {
            if(option in selectedOptions) {
                FilledChip(
                    label = option,
                    modifier = Modifier.clickable {
                        selectedOptions = selectedOptions.minus(option)
                        onSelectionChanged(selectedOptions)
                    }
                )
            }
            else {
                OutlinedChip(
                    label = option,
                    modifier = Modifier.clickable {
                        selectedOptions = selectedOptions.plus(option)
                        onSelectionChanged(selectedOptions)
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