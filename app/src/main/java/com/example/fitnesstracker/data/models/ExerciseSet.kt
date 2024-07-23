package com.example.fitnesstracker.data.models

data class ExerciseSet (
    val id: Int,
    val templateExerciseId: Int,
    val index: Int,
    val reps: Int,
    val weight: Int
)