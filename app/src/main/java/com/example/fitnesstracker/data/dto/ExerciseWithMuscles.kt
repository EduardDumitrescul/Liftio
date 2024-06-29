package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.data.models.Muscle

data class ExerciseWithMuscles(
    val exerciseId: Int,
    var exerciseName: String,
    var exerciseDescription: String,
    var primaryMuscle: String,
    var secondaryMuscles: List<String>,
)