package com.example.fitnesstracker.data.datastore

data class SessionPreferences(
    val existsOngoingWorkout: Boolean,
    val ongoingWorkoutId: Int
)