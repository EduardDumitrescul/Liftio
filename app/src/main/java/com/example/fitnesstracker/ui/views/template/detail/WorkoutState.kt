package com.example.fitnesstracker.ui.views.template.detail

import com.example.fitnesstracker.data.dto.DetailedWorkout
import com.example.fitnesstracker.ui.components.exerciseCard.ExerciseCardState
import com.example.fitnesstracker.ui.components.exerciseCard.toExerciseCardState

data class WorkoutState(
    val id: Int,
    val parentTemplateId: Int,
    val name: String,
    val isBaseTemplate: Boolean,
    val exerciseCardStates: List<ExerciseCardState>,
) {
    companion object {
        fun default(): WorkoutState {
            return WorkoutState(
                id = 0,
                parentTemplateId = 0,
                name = "Default Workout",
                isBaseTemplate = false,
                exerciseCardStates = emptyList()
            )
        }
    }
}

fun DetailedWorkout.toWorkoutState() =
    WorkoutState(
        id = id,
        parentTemplateId = parentTemplateId,
        name = name,
        isBaseTemplate = isBaseTemplate,
        exerciseCardStates = exercisesWithSetsAndMuscles.map { it.toExerciseCardState() }
    )