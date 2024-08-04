package com.example.fitnesstracker.data.dto


data class DetailedWorkout (
    val id: Int,
    val parentTemplateId: Int,
    val name: String,
    val isBaseTemplate: Boolean,
    val exercisesWithSetsAndMuscles: List<DetailedExercise>,
) {
    companion object {
        fun default() = DetailedWorkout(
            id = 0,
            parentTemplateId = 0,
            name = "Default Workout",
            isBaseTemplate = false,
            exercisesWithSetsAndMuscles = emptyList(),
        )
    }
}