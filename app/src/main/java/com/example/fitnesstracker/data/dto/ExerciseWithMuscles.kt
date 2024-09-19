package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.ui.components.MuscleChipRowModel

data class ExerciseWithMuscles(
    val exercise: Exercise,
    val group: String,
    var primaryMuscle: String,
    var secondaryMuscles: List<String>,
) {
    fun getMuscleChipRowModel(): MuscleChipRowModel {
        return MuscleChipRowModel(
            primaryMuscle = "$group ($primaryMuscle)",
            secondaryMuscles = secondaryMuscles,
        )
    }
}