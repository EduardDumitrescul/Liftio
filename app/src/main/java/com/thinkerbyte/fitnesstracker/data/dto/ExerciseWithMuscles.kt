package com.thinkerbyte.fitnesstracker.data.dto

import com.thinkerbyte.fitnesstracker.data.models.Exercise
import com.thinkerbyte.fitnesstracker.data.models.Muscle
import com.thinkerbyte.fitnesstracker.ui.components.MuscleChipRowModel

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