package com.example.fitnesstracker.ui.views.history

import com.example.fitnesstracker.ui.components.workoutCard.WorkoutEntryCardState

data class WorkoutHistoryScreenState(
    val workoutEntryCardStates: List<WorkoutEntryCardState>
) {
    companion object {
        fun default() = WorkoutHistoryScreenState(
            workoutEntryCardStates = emptyList()
        )
    }
}