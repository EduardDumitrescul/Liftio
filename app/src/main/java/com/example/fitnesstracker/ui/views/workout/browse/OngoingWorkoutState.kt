package com.example.fitnesstracker.ui.views.workout.browse

data class OngoingWorkoutState(
    val id: Int,
    val exists: Boolean,
    val name: String,
    val durationInSeconds: Long,
) {
    companion object {
        fun default() = OngoingWorkoutState(
            id = 0,
            exists = false,
            name = "",
            durationInSeconds = 0,
        )
    }
}