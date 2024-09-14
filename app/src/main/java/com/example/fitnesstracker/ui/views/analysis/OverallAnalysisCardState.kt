package com.example.fitnesstracker.ui.views.analysis

data class OverviewAnalysisCardState(
    val workoutCompleted: Int,
    val timeTrainedInSeconds: Int,
    val setsCompleted: Int,
    val selectedTimePeriod: TimePeriodOption,
    val chartData: List<Pair<String, Int>>
) {

    companion object {
        fun default() = OverviewAnalysisCardState(
            workoutCompleted = 145,
            timeTrainedInSeconds = 295,
            setsCompleted = 2693,
            selectedTimePeriod = timePeriodOptions[0],
            chartData = emptyList()
        )

        val timePeriodOptions = listOf(TimePeriodOption.ALL, TimePeriodOption.YEAR, TimePeriodOption.MONTH)
    }
}

enum class TimePeriodOption(val label: String) {
    ALL("all time"),
    YEAR("this year"),
    MONTH("this month")
}

