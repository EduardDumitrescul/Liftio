package com.example.fitnesstracker.ui.views.analysis

data class OverallAnalysisCardState(
    val workoutCompleted: Int,
    val timeTrained: Int,
    val setsCompleted: Int,
    val selectedTimePeriod: String,
    val chartData: List<Pair<String, Int>>
) {

    companion object {
        fun default() = OverallAnalysisCardState(
            workoutCompleted = 145,
            timeTrained = 295,
            setsCompleted = 2693,
            selectedTimePeriod = timePeriodOptions[0],
            chartData = listOf(Pair("Jan", 14), Pair("Feb", 10), Pair("Mar", 24), Pair("Apr", 16), Pair("May", 8), Pair("Jun", 15))
        )

        val timePeriodOptions = listOf("all time", "this year", "this month")
    }
}

