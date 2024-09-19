package com.thinkerbyte.fitnesstracker.ui.views.home

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
import com.thinkerbyte.fitnesstracker.data.dto.WorkoutSummary
import com.thinkerbyte.fitnesstracker.ui.components.appbar.LargeAppBar
import com.thinkerbyte.fitnesstracker.ui.components.button.Fab
import com.thinkerbyte.fitnesstracker.ui.components.button.FilledButton
import com.thinkerbyte.fitnesstracker.ui.components.button.IconButton
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme
import kotlinx.coroutines.launch

private const val TAG = "TemplateBrowseView"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateBrowseView(
    viewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>(),
    navigateToSettings: () -> Unit,
    navigateToTemplateDetailedView: (Int) -> Unit,
    navigateToTemplateEditView: () -> Unit,
    navigateToOngoingWorkout: (Int) -> Unit,
) {
    val ongoingWorkoutState by viewModel.ongoingWorkoutState.collectAsState()
    val templateSummaries by viewModel.templateSummaries.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    StatelessTemplateBrowseView(
        templates = templateSummaries,
        ongoingWorkoutState = ongoingWorkoutState,
        onOngoingWorkoutCardClick = {navigateToOngoingWorkout(ongoingWorkoutState.id)},
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
    ongoingWorkoutState: OngoingWorkoutState,
    onOngoingWorkoutCardClick: () -> Unit,
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
                if(ongoingWorkoutState.exists) {
                    OngoingWorkoutCard(
                        onClick = onOngoingWorkoutCardClick,
                        state = ongoingWorkoutState
                    )
                }
                else {
                    FilledButton(
                        text = "start new workout",
                        onClick = onNewWorkoutButtonClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            items(templates) {template ->
                WorkoutCard(
                    workout = template,
                    onClick = {
                        onCardClicked(template.id)
                    },
                    modifier = Modifier.fillMaxWidth()
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
            ongoingWorkoutState = OngoingWorkoutState.default(),
            {},
            {},
            {},
            {},
            {},
            TopAppBarDefaults.enterAlwaysScrollBehavior()
        )
    }
}