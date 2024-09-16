package com.example.fitnesstracker.data.datastore

data class SessionPreferences(
    val exists: Boolean,
    val workoutId: Int,
    val exercisesCompleted: Int,
    val setsCompleted: Int,
    val duration: Long,
) {
    companion object {
        fun default() = SessionPreferences(
            exists = false,
            workoutId = 0,
            duration = 0,
            exercisesCompleted = 0,
            setsCompleted = 0,
        )
    }
}