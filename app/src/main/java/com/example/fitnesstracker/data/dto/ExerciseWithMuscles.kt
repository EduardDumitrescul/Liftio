package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.ui.views.exercise.browse.MuscleChipRowModel

data class ExerciseWithMuscles(
    val exercise: Exercise,
    var primaryMuscle: String,
    var secondaryMuscles: List<String>,
) {
    fun getMuscleChipRowModel(): MuscleChipRowModel {
        return MuscleChipRowModel(
            primaryMuscle = primaryMuscle,
            secondaryMuscles = secondaryMuscles,
        )
    }
}