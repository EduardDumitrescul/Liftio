package com.example.fitnesstracker.ui.views.template.detail

import com.example.fitnesstracker.data.dto.DetailedWorkout
import com.example.fitnesstracker.ui.components.exerciseCard.ExerciseCardState
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.example.fitnesstracker.ui.components.exerciseCard.toDetailedExercise
import com.example.fitnesstracker.ui.components.exerciseCard.toExerciseCardState
import java.time.LocalDateTime

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
                exerciseCardStates = emptyList(),
            )
        }
    }

    fun updateSet(set: SetState): WorkoutState {
        val updatedExercises = exerciseCardStates.map { exercise->
            exercise.updateSet(set)
        }
        return this.copy(
            exerciseCardStates = updatedExercises
        )
    }
}

fun DetailedWorkout.toWorkoutState() =
    WorkoutState(
        id = id,
        parentTemplateId = parentTemplateId,
        name = name,
        isBaseTemplate = isBaseTemplate,
        exerciseCardStates = detailedExercises.map { it.toExerciseCardState() },
    )

fun WorkoutState.toDetailedWorkout(timeStarted: LocalDateTime = LocalDateTime.now(), duration: Long = 0) =
    DetailedWorkout(
        id = id,
        parentTemplateId = parentTemplateId,
        name = name,
        isBaseTemplate = isBaseTemplate,
        detailedExercises = exerciseCardStates.map { it.toDetailedExercise() },
        timeStarted = timeStarted,
        duration = duration
    )