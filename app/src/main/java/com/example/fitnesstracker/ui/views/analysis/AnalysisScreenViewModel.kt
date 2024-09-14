package com.example.fitnesstracker.ui.views.analysis

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.OverviewStatistics
import com.example.fitnesstracker.services.AnalysisService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.max

private const val TAG = "AnalysisScreenViewModel"

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
        val workoutDatesFlow: Flow<List<LocalDateTime>> = analysisService.getWorkoutDates()
        viewModelScope.launch {
            combine(overviewStatisticsFlow, workoutDatesFlow) { overviewStatistics, workoutDates ->
                Pair(overviewStatistics, workoutDates)
            }.collect { (overviewStatistics, workoutDates) ->
                // Handle both results simultaneously
                val overviewAnalysisCardState = _state.value.overviewAnalysisCardState.copy(
                    workoutCompleted = overviewStatistics.workoutsCompleted,
                    timeTrainedInSeconds = overviewStatistics.timeTrained,
                    setsCompleted = overviewStatistics.setsCompleted,
                    chartData = computeChartData(_state.value.overviewAnalysisCardState.selectedTimePeriod, workoutDates)
                )
                _state.update {
                    _state.value.copy(overviewAnalysisCardState = overviewAnalysisCardState)
                }
            }
        }

    }

    private fun computeChartData(timePeriodOption: TimePeriodOption, dates: List<LocalDateTime>): List<Pair<String, Int>> {
        Log.d(TAG, "computeChartData() $timePeriodOption")
        if (dates.isEmpty()) {
            return emptyList()
        }

        return when (timePeriodOption) {
            TimePeriodOption.ALL -> {
                // Group by year
                val groupedByYear = dates.groupBy { it.year }
                val minYear = max(groupedByYear.keys.minOrNull() ?: (LocalDateTime.now().year - 5), LocalDateTime.now().year - 5)
                val currentYear = LocalDateTime.now().year
                val output: MutableList<Pair<String, Int>> = mutableListOf()

                for (year in minYear..currentYear) {
                    val count = groupedByYear[year]?.size ?: 0
                    output.add(Pair(year.toString(), count))
                }
                output
            }

            TimePeriodOption.YEAR -> {
                // Group by month for the current year
                val currentYear = LocalDateTime.now().year
                val datesInCurrentYear = dates.filter { it.year == currentYear }
                val groupedByMonth = datesInCurrentYear.groupBy { it.monthValue }
                val output: MutableList<Pair<String, Int>> = mutableListOf()

                for (month in 1..12) {
                    val monthName = LocalDateTime.of(currentYear, month, 1, 0, 0).month.name
                    val count = groupedByMonth[month]?.size ?: 0
                    output.add(Pair(monthName, count))
                }
                output
            }

            TimePeriodOption.MONTH -> {
                // Group by day for the current month
                val now = LocalDateTime.now()
                val currentMonthDates = dates.filter { it.year == now.year && it.monthValue == now.monthValue }
                val groupedByDay = currentMonthDates.groupBy { it.dayOfMonth }
                val output: MutableList<Pair<String, Int>> = mutableListOf()

                for (day in 1..now.toLocalDate().lengthOfMonth()) {
                    val count = groupedByDay[day]?.size ?: 0
                    output.add(Pair(day.toString(), count))
                }
                output
            }
        }
    }

}