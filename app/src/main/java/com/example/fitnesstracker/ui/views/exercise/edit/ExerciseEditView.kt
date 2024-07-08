package com.example.fitnesstracker.ui.views.exercise.edit

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.button.FilledButton
import com.example.fitnesstracker.ui.components.button.OutlinedButton
import com.example.fitnesstracker.ui.components.chip.MultiChoiceChipGroup
import com.example.fitnesstracker.ui.components.chip.SingleChoiceChipGroup
import com.example.fitnesstracker.ui.components.textfield.FilledTextField
import com.example.fitnesstracker.ui.theme.AppTheme
import kotlinx.coroutines.launch

private const val TAG = "ExerciseEditView"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEditView(
    viewModel: ExerciseEditViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val muscleNames by viewModel.muscleNames.collectAsState()
    val exercise by viewModel.exerciseWithMuscles.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Log.d(TAG, exercise.toString())

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            LargeAppBar(
                title = "Edit Exercise",
                showNavigationIcon = true,
                onNavigationIconClick = {navigateBack()}
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
                        if(exercise.exercise.name == "") {
                            scope.launch { snackbarHostState.showSnackbar("Please enter the name for the exercise") }
                        }
                        else if(exercise.primaryMuscle == "") {
                            scope.launch { snackbarHostState.showSnackbar("Please select the targeted muscle") }
                        }
                        else {
                            viewModel.save()
                            navigateBack()
                        }
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
                    color = AppTheme.colors.onBackground
                )
                FilledTextField(
                    text = exercise.exercise.name,
                    onValueChange = { viewModel.updateExerciseName(it) },
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
                    text = exercise.exercise.description,
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
                    selected = exercise.exercise.equipment,
                    onSelectionChanged = { viewModel.updateEquipment(it) }
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
                    selected = exercise.primaryMuscle,
                    onSelectionChanged = { viewModel.updatePrimaryMuscle(it) }
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
                    selected = exercise.secondaryMuscles.toSet(),
                    onSelectionChanged = { viewModel.updateSecondaryMuscles(it) }
                )
            }
        }
    }
}
