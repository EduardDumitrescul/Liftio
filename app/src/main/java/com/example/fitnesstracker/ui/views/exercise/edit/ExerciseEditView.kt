package com.example.fitnesstracker.ui.views.exercise.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.button.TwoButtonRow
import com.example.fitnesstracker.ui.components.chip.MultiChoiceChipGroupField
import com.example.fitnesstracker.ui.components.chip.SingleChoiceChipGroupField
import com.example.fitnesstracker.ui.components.textfield.StringValueEditField
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

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            LargeAppBar(
                title = "Edit Exercise",
                showNavigationIcon = true,
                onNavigationIconClick = navigateBack
            )
        },
        bottomBar = {
            TwoButtonRow(
                primaryButtonText = "Save",
                onPrimaryButtonClick = {
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
                secondaryButtonText = "Cancel",
                onSecondaryButtonClick = navigateBack,
                modifier = Modifier.padding(16.dp)
            )
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
            NameEditField(exercise, viewModel)

            DescriptionEditField(exercise, viewModel)

            EquipmentSelect(exercise, viewModel)

            PrimaryMuscleSelect(muscleNames, exercise, viewModel)

            SecondaryMusclesSelect(muscleNames, exercise, viewModel)
        }
    }
}

@Composable
private fun SecondaryMusclesSelect(
    muscleNames: List<String>,
    exercise: ExerciseWithMuscles,
    viewModel: ExerciseEditViewModel
) {
    MultiChoiceChipGroupField(
        title = "Secondary Muscles",
        options = muscleNames,
        selectedOptions = exercise.secondaryMuscles.toSet(),
        onSelectionChanged = { viewModel.updateSecondaryMuscles(it) }
    )
}

@Composable
private fun PrimaryMuscleSelect(
    muscleNames: List<String>,
    exercise: ExerciseWithMuscles,
    viewModel: ExerciseEditViewModel
) {
    SingleChoiceChipGroupField(
        title = "Target Muscle",
        options = muscleNames,
        selectedOption = exercise.primaryMuscle,
        onSelectionChanged = { viewModel.updatePrimaryMuscle(it) }
    )
}

@Composable
private fun EquipmentSelect(
    exercise: ExerciseWithMuscles,
    viewModel: ExerciseEditViewModel
) {
    SingleChoiceChipGroupField(
        title = "Necessary Equipment",
        options = listOf("barbell", "dumbbells", "machine", "cables", "none"),
        selectedOption = exercise.exercise.equipment,
        onSelectionChanged = { viewModel.updateEquipment(it) }
    )
}

@Composable
private fun DescriptionEditField(
    exercise: ExerciseWithMuscles,
    viewModel: ExerciseEditViewModel
) {
    StringValueEditField(
        title = "Description",
        initialFieldValue = exercise.exercise.description,
        onValueChanged = { viewModel.updateExerciseDescription(it) },
        placeholderText = "description\n(optional)",
        singleLine = false,
        minLines = 5,
    )
}

@Composable
private fun NameEditField(
    exercise: ExerciseWithMuscles,
    viewModel: ExerciseEditViewModel
) {
    StringValueEditField(
        title = "Name",
        initialFieldValue = exercise.exercise.name,
        onValueChanged = { viewModel.updateExerciseName(it) },
        placeholderText = "exercise name"
    )
}
