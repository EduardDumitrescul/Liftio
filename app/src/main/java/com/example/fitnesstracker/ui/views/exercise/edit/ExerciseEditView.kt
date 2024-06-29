package com.example.fitnesstracker.ui.views.exercise.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.textfield.FilledTextField
import com.example.fitnesstracker.ui.theme.AppTheme
import com.example.fitnesstracker.ui.views.exercise.ExerciseListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEditView(
    viewModel: ExerciseEditViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            LargeAppBar(
                title = "Edit Exercise",
                showNavigationIcon = true,
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
        }
    }
}

@Preview
@Composable
fun PreviewExerciseEditView() {
    AppTheme {
        ExerciseEditView()
    }
}