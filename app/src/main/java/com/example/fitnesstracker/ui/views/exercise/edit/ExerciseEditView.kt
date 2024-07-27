package com.example.fitnesstracker.ui.views.exercise.edit

import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.MultiChoiceChipGroupField
import com.example.fitnesstracker.ui.components.SingleChoiceChipGroupField
import com.example.fitnesstracker.ui.components.StringValueEditField
import com.example.fitnesstracker.ui.components.TwoButtonBottomBar
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
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
            TwoButtonBottomBar(
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
                onSecondaryButtonClick = navigateBack
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
            StringValueEditField(
                title = "Name",
                initialFieldValue = exercise.exercise.name,
                onValueChanged =  { viewModel.updateExerciseName(it) },
                placeholderText = "exercise name"
            )

            StringValueEditField(
                title = "Description",
                initialFieldValue = exercise.exercise.description,
                onValueChanged =  { viewModel.updateExerciseDescription(it) },
                placeholderText = "description\n(optional)",
                singleLine = false,
                minLines = 5,
            )

            SingleChoiceChipGroupField(
                title = "Necessary Equipment",
                options =  listOf("barbell", "dumbbells", "machine", "cables", "none"),
                selectedOption = exercise.exercise.equipment,
                onSelectionChanged = { viewModel.updateEquipment(it) }
            )

            SingleChoiceChipGroupField(
                title = "Target Muscle",
                options =  muscleNames,
                selectedOption = exercise.primaryMuscle,
                onSelectionChanged = { viewModel.updatePrimaryMuscle(it) }
            )

            MultiChoiceChipGroupField(
                title = "Secondary Muscles",
                options = muscleNames,
                selectedOptions = exercise.secondaryMuscles.toSet(),
                onSelectionChanged = { viewModel.updateSecondaryMuscles(it) }
            )
        }
    }
}
