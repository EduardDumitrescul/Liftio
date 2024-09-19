package com.thinkerbyte.fitnesstracker.data.datastore

import java.time.LocalDateTime

data class SessionPreferences(
    val exists: Boolean,
    val workoutId: Int,
    val exercisesCompleted: Int,
    val setsCompleted: Int,
    val timeStarted: LocalDateTime,
) {
    companion object {
        fun default() = SessionPreferences(
            exists = false,
            workoutId = 0,
            timeStarted = LocalDateTime.now(),
            exercisesCompleted = 0,
            setsCompleted = 0,
        )
    }
}