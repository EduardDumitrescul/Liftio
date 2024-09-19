package com.thinkerbyte.fitnesstracker.ui.views.history.exercise

import com.thinkerbyte.fitnesstracker.data.dto.DateWithSets

data class ExerciseHistoryScreenState(
    val exerciseName: String,
    val datesWithSets: List<DateWithSets>
) {
    companion object {
        fun default() = ExerciseHistoryScreenState(
            exerciseName = "exercise name",
            datesWithSets = emptyList(),
        )
    }
}