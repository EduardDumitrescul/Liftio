package com.example.fitnesstracker.ui.components.exerciseCard

import com.example.fitnesstracker.data.dto.DetailedExercise
import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.data.models.Muscle
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.toSetState

data class ExerciseCardState(
    val exercise: Exercise,
    val templateExerciseCrossRefId: Int,
    val primaryMuscle: Muscle,
    val secondaryMuscles: List<Muscle>,
    val sets: List<SetState>
) {
    companion object {
        fun default(): ExerciseCardState {
            return ExerciseCardState(
                exercise = Exercise.default(),
                templateExerciseCrossRefId = 0,
                primaryMuscle = Muscle.default(),
                secondaryMuscles = emptyList(),
                sets = emptyList()
            )
        }
    }
}

fun DetailedExercise.toExerciseCardState() =
    ExerciseCardState(
        exercise = exercise,
        templateExerciseCrossRefId = templateExerciseCrossRefId,
        primaryMuscle = primaryMuscle,
        secondaryMuscles = secondaryMuscles,
        sets = sets.map {it.toSetState()}
    )
