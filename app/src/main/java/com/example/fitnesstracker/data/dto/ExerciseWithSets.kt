package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.data.models.ExerciseSet

data class ExerciseWithSets (
    val exercise: Exercise,
    val sets: List<ExerciseSet>
)