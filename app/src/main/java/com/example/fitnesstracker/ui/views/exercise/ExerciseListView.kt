package com.example.fitnesstracker.ui.views.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.textfield.FilledTextField
import com.example.fitnesstracker.ui.theme.AppTheme
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListView(
    navigateBack: () -> Unit,
    viewModel: ExerciseListViewModel = hiltViewModel<ExerciseListViewModel>(),
) {
    val exerciseSummaries by viewModel.filteredExerciseSummaries.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeAppBar(
                title = "Exercises",
                onNavigationIconClick = navigateBack,
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Rounded.Add,
                            "add new exercise",
                            tint = AppTheme.colors.onBackground,
                            modifier = Modifier.size(32.dp),
                        )
                    }
                }
            )
        },
        containerColor = AppTheme.colors.background
    ) {innerPadding ->
        LazyColumn(
            Modifier
                .padding(bottom = innerPadding.calculateBottomPadding())
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                FilledTextField(
                    onValueChange = {viewModel.updateFilter(it)},
                    placeholderText = "exercise name"
                )
            }
            items(exerciseSummaries) {
                ExerciseRow(it)
            }
            items(exerciseSummaries) {
                ExerciseRow(it)
            }
            items(exerciseSummaries) {
                ExerciseRow(it)
            }
            items(exerciseSummaries) {
                ExerciseRow(it)
            }
            items(exerciseSummaries) {
                ExerciseRow(it)
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun Preview() {
    AppTheme {
        ExerciseListView(
            navigateBack = {}
        )
    }

}