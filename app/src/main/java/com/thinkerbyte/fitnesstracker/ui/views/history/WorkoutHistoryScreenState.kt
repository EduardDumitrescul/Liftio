package com.thinkerbyte.fitnesstracker.ui.views.history

import com.thinkerbyte.fitnesstracker.ui.components.workoutCard.WorkoutEntryCardState

data class WorkoutHistoryScreenState(
    val workoutEntryCardStates: List<WorkoutEntryCardState>
) {
    companion object {
        fun default() = WorkoutHistoryScreenState(
            workoutEntryCardStates = emptyList()
        )
    }
}