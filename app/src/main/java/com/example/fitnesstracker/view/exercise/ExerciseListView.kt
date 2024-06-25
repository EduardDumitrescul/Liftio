package com.example.fitnesstracker.view.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnesstracker.view.components.appbar.LargeAppBar
import com.example.fitnesstracker.view.components.textfield.FilledTextField
import com.example.fitnesstracker.view.theme.AppTheme
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ExerciseListView(
    viewModel: ExerciseListViewModel = hiltViewModel<ExerciseListViewModel>(),
) {
    val exerciseSummaries by viewModel.exerciseSummaries.collectAsState()

    LazyColumn(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            LargeAppBar(title = "Exercises")
        }
        item {
            FilledTextField(
                onValueChange = {},
                placeholderText = "exercise name"
            )
        }


        items(exerciseSummaries) {
            ExerciseRow(it)
        }
    }
}


@Composable
@Preview(showBackground = true)
fun Preview() {
    AppTheme {
        ExerciseListView()
    }

}