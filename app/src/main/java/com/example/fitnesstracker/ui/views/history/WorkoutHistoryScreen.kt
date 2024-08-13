package com.example.fitnesstracker.ui.views.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.workoutCard.WorkoutEntryCard
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutHistoryScreen(
    viewModel: WorkoutHistoryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()


    Scaffold(
        topBar = {
            LargeAppBar(
                title = "History",
                showNavigationIcon = false
            )
        },
        containerColor = AppTheme.colors.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.workoutEntryCardStates) { cardState ->
                WorkoutEntryCard(state = cardState)
            }
        }
    }
}