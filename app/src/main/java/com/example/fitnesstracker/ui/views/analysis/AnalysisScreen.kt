package com.example.fitnesstracker.ui.views.analysis

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    viewModel: AnalysisScreenViewModel = hiltViewModel()
) {
    val state: AnalysisScreenState by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            LargeAppBar(
                title = "Analysis",
                scrollBehavior = scrollBehavior,
                showNavigationIcon = false,
            )
        },
        containerColor = AppTheme.colors.background,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            OverviewAnalysisCard(
                state = state.overviewAnalysisCardState,
                onTimePeriodSelectionChanged = {
                    viewModel.updateOverviewAnalysisTimePeriodOption(TimePeriodOption.fromString(it))
                }
            )
        }

    }
}

