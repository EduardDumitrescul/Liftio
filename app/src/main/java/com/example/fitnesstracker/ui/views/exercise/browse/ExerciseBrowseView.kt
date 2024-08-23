package com.example.fitnesstracker.ui.views.exercise.browse

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.TopAppBarScrollBehavior
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.theme.AppTheme

private const val TAG = "ExerciseListView"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseBrowseView(
    viewModel: ExerciseBrowseViewModel = hiltViewModel<ExerciseBrowseViewModel>(),
    navigateBack: () -> Unit,
    onExerciseClick: (Int) -> Unit,
    onActionClick: () -> Unit,
) {
    val exerciseSummaries by viewModel.filteredExerciseSummaries.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var searchValue by remember { mutableStateOf("")}

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(scrollBehavior, onActionClick)
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
                SearchField(
                    searchValue,
                    onValueChange = {
                        searchValue = it
                        viewModel.updateFilter(it)
                    }
                )
            }
            items(exerciseSummaries) {
                ExerciseRow(
                    it,
                    modifier = Modifier.clickable(
                        onClick = {onExerciseClick(it.exercise.id)}
                    ).fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onActionClick: () -> Unit
) {
    LargeAppBar(
        title = "Exercises",
        showNavigationIcon = false,
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    Icons.Rounded.Add,
                    "add new exercise",
                    tint = AppTheme.colors.onBackground,
                    modifier = Modifier.size(AppTheme.dimensions.iconNormal),
                )
            }
        }
    )
}


@Composable
@Preview(showBackground = true)
fun Preview() {
    AppTheme {
        ExerciseBrowseView(
            navigateBack = {},
            onExerciseClick = {},
            onActionClick = {}
        )
    }

}