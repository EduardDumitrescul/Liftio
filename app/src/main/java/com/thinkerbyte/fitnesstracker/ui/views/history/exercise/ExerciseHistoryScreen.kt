package com.thinkerbyte.fitnesstracker.ui.views.history.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thinkerbyte.fitnesstracker.ui.components.appbar.LargeAppBar
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme
import com.thinkerbyte.fitnesstracker.ui.views.history.exercise.dateWithSets.DateWithSetsColumn

//TODO extract Scaffold to custom component

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseHistoryScreen(
    viewModel: ExerciseHistoryViewModel = hiltViewModel<ExerciseHistoryViewModel>(),
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            LargeAppBar(
                title = state.exerciseName,
                onNavigationIconClick = navigateBack,
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = AppTheme.colors.background,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.datesWithSets) { dateWithSetsColumnState ->
                DateWithSetsColumn(
                    state = dateWithSetsColumnState
                )
            }
        }
    }
}