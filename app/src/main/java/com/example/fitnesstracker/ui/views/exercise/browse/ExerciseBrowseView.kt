package com.example.fitnesstracker.ui.views.exercise.browse

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.textfield.FilledTextField
import com.example.fitnesstracker.ui.theme.AppTheme
import androidx.hilt.navigation.compose.hiltViewModel

private const val TAG = "ExerciseListView"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseBrowseView(
    navigateBack: () -> Unit,
    navigateToExerciseEditView: (Int) -> Unit,
    viewModel: ExerciseBrowseViewModel = hiltViewModel<ExerciseBrowseViewModel>(),
) {
    val exerciseSummaries by viewModel.filteredExerciseSummaries.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var text by remember { mutableStateOf("")}

    Log.d(TAG, exerciseSummaries.toString())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeAppBar(
                title = "Exercises",
                onNavigationIconClick = navigateBack,
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = {navigateToExerciseEditView(0)}) {
                        Icon(
                            Icons.Rounded.Add,
                            "add new exercise",
                            tint = AppTheme.colors.onBackground,
                            modifier = Modifier.size(AppTheme.dimensions.iconNormal),
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
                .padding(horizontal = AppTheme.dimensions.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingLarge)
        ) {
            item {
                FilledTextField(
                    text = text,
                    onValueChange = {
                        text = it
                        viewModel.updateFilter(it)},
                    placeholderText = "exercise name",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "search",
                            modifier = Modifier.size(AppTheme.dimensions.iconSmall)
                        )
                    }
                )
            }
            items(exerciseSummaries) {
                ExerciseRow(
                    it,
                    modifier = Modifier.clickable(
                        onClick = {navigateToExerciseEditView(it.exercise.id)}
                    )
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun Preview() {
    AppTheme {
        ExerciseBrowseView(
            navigateBack = {},
            navigateToExerciseEditView = {},
        )
    }

}