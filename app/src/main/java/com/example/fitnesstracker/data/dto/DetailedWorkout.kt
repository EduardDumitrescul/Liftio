package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.data.models.Workout

data class DetailedWorkout (
    val workout: Workout,
    val exercisesWithSetsAndMuscles: List<DetailedExercise>,
) {
    companion object {
        fun default() = DetailedWorkout(
            workout = Workout.default(),
            exercisesWithSetsAndMuscles = emptyList(),
        )
    }
}