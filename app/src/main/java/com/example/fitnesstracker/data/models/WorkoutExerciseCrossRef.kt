package com.example.fitnesstracker.data.models

data class WorkoutExerciseCrossRef(
    val id: Int,
    val workoutId: Int,
    val exerciseId: Int,
    val index: Int,
)