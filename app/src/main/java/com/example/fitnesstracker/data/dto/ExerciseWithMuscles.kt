package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.data.models.Muscle
import com.example.fitnesstracker.ui.components.MuscleChipRowModel

data class ExerciseWithMuscles(
    val exercise: Exercise,
    val primaryMuscle: Muscle,
    var secondaryMuscles: List<Muscle>,
) {
    fun getMuscleChipRowModel(): MuscleChipRowModel {
        return MuscleChipRowModel(
            primaryMuscle = "${primaryMuscle.group} (${primaryMuscle.name})",
            secondaryMuscles = secondaryMuscles.map { it.name },
        )
    }
}