package com.example.fitnesstracker.ui.views.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.OverviewStatistics
import com.example.fitnesstracker.services.AnalysisService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisScreenViewModel @Inject constructor(
    private val analysisService: AnalysisService,
): ViewModel() {
    private val _state: MutableStateFlow<AnalysisScreenState> = MutableStateFlow(AnalysisScreenState.default())
    val state: StateFlow<AnalysisScreenState> get() = _state.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        val overviewStatisticsFlow: Flow<OverviewStatistics> = analysisService.getOverviewStatistics()
        viewModelScope.launch {
            overviewStatisticsFlow.collect { overviewStatistics ->
                val overviewAnalysisCardState = OverviewAnalysisCardState(
                    workoutCompleted = overviewStatistics.workoutsCompleted,
                    timeTrainedInSeconds = overviewStatistics.timeTrained,
                    setsCompleted = overviewStatistics.setsCompleted,
                    selectedTimePeriod = "all time",
                    chartData = emptyList()
                )
                _state.update {
                    AnalysisScreenState(
                        overviewAnalysisCardState = overviewAnalysisCardState
                    )
                }

            }
        }
    }
}