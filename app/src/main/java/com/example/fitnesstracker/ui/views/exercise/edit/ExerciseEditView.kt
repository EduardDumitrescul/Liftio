package com.example.fitnesstracker.ui.views.exercise.edit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.theme.AppTheme
import com.example.fitnesstracker.ui.views.exercise.ExerciseListViewModel

@Composable
fun ExerciseEditView(
    viewModel: ExerciseEditViewModel = hiltViewModel()
) {
    Text("Exercise Edit, ${viewModel.exerciseId}")
}

@Preview
@Composable
fun PreviewExerciseEditView() {
    AppTheme {
        ExerciseEditView()
    }
}