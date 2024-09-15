package com.example.fitnesstracker.ui.views.workout.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.data.dto.WorkoutSummary
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.button.Fab
import com.example.fitnesstracker.ui.components.button.FilledButton
import com.example.fitnesstracker.ui.components.button.IconButton
import com.example.fitnesstracker.ui.theme.AppTheme
import kotlinx.coroutines.launch

//TODO add card with current ongoing workout
//TODO don't allow user to start other workouts if one is already in progress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateBrowseView(
    viewModel: TemplateBrowseViewModel = hiltViewModel<TemplateBrowseViewModel>(),
    navigateToSettings: () -> Unit,
    navigateToTemplateDetailedView: (Int) -> Unit,
    navigateToTemplateEditView: () -> Unit,
    navigateToOngoingWorkout: (Int) -> Unit,
) {
    val templateSummaries by viewModel.templateSummaries.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    StatelessTemplateBrowseView(
        templates = templateSummaries,
        onSettingsButtonClick = navigateToSettings,
        onCardClicked = navigateToTemplateDetailedView,
        onFabClicked = navigateToTemplateEditView,
        onNewWorkoutButtonClick = {
            coroutineScope.launch {
                val id = viewModel.createBlankWorkout()
                navigateToOngoingWorkout(id)
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatelessTemplateBrowseView(
    templates: List<WorkoutSummary>,
    onSettingsButtonClick: () -> Unit,
    onCardClicked: (Int) -> Unit = {},
    onFabClicked: () -> Unit,
    onNewWorkoutButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    Scaffold(
        topBar = {
            LargeAppBar(
                title = "Templates",
                showNavigationIcon = false,
                actions = {
                    SettingsButton(
                        onClick = onSettingsButtonClick
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            Fab(
                imageVector = Icons.Rounded.Add,
                description = "add new template",
                onClick = onFabClicked
            )
        },
        containerColor = AppTheme.colors.background,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = AppTheme.dimensions.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingLarge)
        ) {
            item {
                FilledButton(
                    text = "start new workout",
                    onClick = onNewWorkoutButtonClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(templates) {template ->
                WorkoutCard(
                    workout = template,
                    onClick = {
                        onCardClicked(template.id)
                    }
                )
            }

        }
    }
}

@Composable
private fun SettingsButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        imageVector = Icons.Rounded.Settings,
        contentDescription = "open settings",
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.onBackground,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewTemplateBrowseView() {
    val template = WorkoutSummary(
        id = 0,
        name = "Push Workout",
        workedMuscles = listOf("chest", "shoulders", "triceps"),
        exerciseList = listOf(
            "3 x bench press",
            "4 x shoulder press",
            "3 x triceps pushdown",
            "2 x incline press",
            "5 x lateral raise")
    )
    AppTheme {
        StatelessTemplateBrowseView(
            templates = listOf(template, template, template, template),
            {},
            {},
            {},
            {},
            TopAppBarDefaults.enterAlwaysScrollBehavior()
        )
    }
}