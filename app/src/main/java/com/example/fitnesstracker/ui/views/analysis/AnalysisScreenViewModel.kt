package com.example.fitnesstracker.ui.views.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.OverviewStatistics
import com.example.fitnesstracker.services.AnalysisService
import com.example.fitnesstracker.ui.views.analysis.overallCard.TimePeriodOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Locale
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class AnalysisScreenViewModel @Inject constructor(
    private val analysisService: AnalysisService,
): ViewModel() {
    private val _state: MutableStateFlow<AnalysisScreenState> = MutableStateFlow(AnalysisScreenState.default())
    val state: StateFlow<AnalysisScreenState> get() = _state.asStateFlow()

    private val _overviewAnalysisTimePeriodOption: MutableStateFlow<TimePeriodOption> = MutableStateFlow(
        TimePeriodOption.ALL)

    private var fetchDataJob: Job? = null

    init {
        fetchData()
    }

    private fun fetchData() {
        fetchDataJob?.cancel()

        val selectedTimePeriod = _overviewAnalysisTimePeriodOption.value
        val now = LocalDateTime.now()
        val from: LocalDateTime
        val to: LocalDateTime

        // Determine `from` and `to` based on the selected time period
        when (selectedTimePeriod) {
            TimePeriodOption.ALL -> {
                // Define a broad range for "ALL" period, e.g., the last 5 years
                from = now.minusYears(5)
                to = now
            }
            TimePeriodOption.YEAR -> {
                // Define a range for the current year
                from = now.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0)
                to = now.withDayOfYear(now.toLocalDate().lengthOfYear()).withHour(23).withMinute(59).withSecond(59)
            }
            TimePeriodOption.MONTH -> {
                // Define a range for the current month
                from = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0)
                to = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59)
            }
        }

        fetchDataJob = viewModelScope.launch {
            val overviewStatisticsFlow: Flow<OverviewStatistics> = analysisService.getOverviewStatistics(from, to)
            val workoutDatesFlow: Flow<List<LocalDateTime>> = analysisService.getWorkoutDates(from, to)

            combine(
                _overviewAnalysisTimePeriodOption,
                overviewStatisticsFlow,
                workoutDatesFlow
            ) { _, overviewStatistics, workoutDates ->
                Pair(overviewStatistics, workoutDates)
            }.collect { (overviewStatistics, workoutDates) ->
                // Handle both results simultaneously
                val overviewAnalysisCardState = _state.value.overviewAnalysisCardState.copy(
                    workoutCompleted = overviewStatistics.workoutsCompleted,
                    timeTrainedInSeconds = overviewStatistics.timeTrained,
                    setsCompleted = overviewStatistics.setsCompleted,
                    selectedTimePeriod = _overviewAnalysisTimePeriodOption.value,
                    chartData = computeChartData(_overviewAnalysisTimePeriodOption.value, workoutDates)
                )
                _state.update {
                    _state.value.copy(overviewAnalysisCardState = overviewAnalysisCardState)
                }
            }
        }
    }



    private fun computeChartData(timePeriodOption: TimePeriodOption, dates: List<LocalDateTime>): List<Pair<String, Int>> {
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
                    val monthName =
                        LocalDateTime.of(currentYear, month, 1, 0, 0).month.name.substring(0, 3).lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
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

    fun updateOverviewAnalysisTimePeriodOption(option: TimePeriodOption) {
        _overviewAnalysisTimePeriodOption.update { option }
        fetchData()
    }

}