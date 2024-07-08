package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.data.models.Muscle

data class ExerciseWithMuscles(
    val exercise: Exercise,
    var primaryMuscle: String,
    var secondaryMuscles: List<String>,
)