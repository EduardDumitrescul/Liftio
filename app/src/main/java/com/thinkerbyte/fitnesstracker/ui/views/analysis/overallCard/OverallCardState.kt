package com.thinkerbyte.fitnesstracker.ui.views.analysis.overallCard

import java.security.InvalidParameterException

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

        val timePeriodOptions = listOf(
            TimePeriodOption.ALL,
            TimePeriodOption.YEAR,
            TimePeriodOption.MONTH
        )
    }
}

enum class TimePeriodOption(val label: String) {
    ALL("all time"),
    YEAR("this year"),
    MONTH("this month");

    companion object {
        fun fromString(label: String): TimePeriodOption {
            return when(label) {
                ALL.label -> ALL
                YEAR.label -> YEAR
                MONTH.label -> MONTH
                else -> throw InvalidParameterException("There is no TimePeriodOption with the label $label")
            }
        }
    }
}

