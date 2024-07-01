package com.example.fitnesstracker.ui.views.exercise.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.button.FilledButton
import com.example.fitnesstracker.ui.components.button.OutlinedButton
import com.example.fitnesstracker.ui.components.chip.MultiChoiceChipGroup
import com.example.fitnesstracker.ui.components.chip.SingleChoiceChipGroup
import com.example.fitnesstracker.ui.components.textfield.FilledTextField
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEditView(
    viewModel: ExerciseEditViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val muscleNames by viewModel.muscleNames.collectAsState()

    Scaffold(
        topBar = {
            LargeAppBar(
                title = "Edit Exercise",
                showNavigationIcon = true,
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppTheme.dimensions.paddingNormal)
                    .padding(horizontal = AppTheme.dimensions.paddingLarge),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingLarge)
            ) {
                OutlinedButton(
                    text = "Cancel",
                    onClick = navigateBack,
                    modifier = Modifier.weight(0.5f)
                )
                FilledButton(
                    text = "Save",
                    onClick = {
                        viewModel.save()
                        navigateBack()
                    },
                    modifier = Modifier.weight(0.5f)
                )
            }
        },
        containerColor = AppTheme.colors.background,
        contentColor = AppTheme.colors.onBackground,
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .padding(innerPaddings)
                .padding(horizontal = AppTheme.dimensions.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingLarge)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
                modifier = Modifier.padding(vertical = AppTheme.dimensions.paddingNormal)
            ) {
                Text(
                    "Name",
                    style = AppTheme.typography.caption,
                    color=  AppTheme.colors.onBackground
                )
                FilledTextField(
                    onValueChange = {viewModel.updateExerciseName(it)},
                    placeholderText = "exercise name"
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
                modifier = Modifier.padding(vertical = AppTheme.dimensions.paddingNormal)
            ) {
                Text(
                    "Description",
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.onBackground
                )
                FilledTextField(
                    onValueChange = { viewModel.updateExerciseDescription(it) },
                    placeholderText = "description\n(optional)",
                    singleLine = false,
                    minLines = 5,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
                modifier = Modifier.padding(vertical = AppTheme.dimensions.paddingNormal)
            ) {
                Text(
                    text = "Necessary Equipment",
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.onBackground
                )

                SingleChoiceChipGroup(
                    options = listOf("barbell", "dumbbells", "machine", "cables", "none"),
                    onSelectionChanged = {viewModel.updateEquipment(it)}
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
                modifier = Modifier.padding(vertical = AppTheme.dimensions.paddingNormal)
            ) {
                Text(
                    text = "Target Muscle",
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.onBackground
                )
                SingleChoiceChipGroup(
                    options = muscleNames,
                    onSelectionChanged = {viewModel.updatePrimaryMuscle(it)}
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
                modifier = Modifier.padding(vertical = AppTheme.dimensions.paddingNormal)
            ) {
                Text(
                    text = "Secondary Muscles",
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.onBackground
                )

                MultiChoiceChipGroup(
                    options = muscleNames,
                    onSelectionChanged = {viewModel.updateSecondaryMuscles(it)}
                )
            }
        }
    }
}