package com.thinkerbyte.fitnesstracker.data.dto

import com.thinkerbyte.fitnesstracker.data.models.Exercise
import com.thinkerbyte.fitnesstracker.data.models.ExerciseSet
import com.thinkerbyte.fitnesstracker.data.models.Muscle

data class DetailedExercise(
    val exercise: Exercise,
    val templateExerciseCrossRefId: Int,
    val primaryMuscle: Muscle,
    val secondaryMuscles: List<Muscle>,
    val sets: List<ExerciseSet>
) {
    companion object {
        fun default() = DetailedExercise(
            Exercise.default(),
            0,
            Muscle.default(),
            emptyList(),
            emptyList(),
        )
    }
}