package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.ui.views.exercise.MuscleChipRowModel

data class ExerciseSummary(
    val equipmentType: String,
    val exerciseName: String,
    val primaryMuscle: String,
    val secondaryMuscles: List<String>
) {
    fun getMuscleChipRowModel(): MuscleChipRowModel {
        return MuscleChipRowModel(
            primaryMuscle = primaryMuscle,
            secondaryMuscles = secondaryMuscles,
        )
    }
}