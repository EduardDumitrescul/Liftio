package com.example.fitnesstracker.ui.components.exerciseCard.setRow

import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.ui.components.exerciseCard.Progress

data class SetState(
    val id: Int,
    val workoutExerciseId: Int,
    val index: Int,
    val reps: Int,
    val weight: Int,
    val progress: Progress
) {
    fun toExerciseSet() =
        ExerciseSet(
            id = id,
            workoutExerciseId = workoutExerciseId,
            index = index,
            reps = reps,
            weight = weight
        )
}

fun ExerciseSet.toSetState(status: Progress = Progress.DONE) =
    SetState(
        id = id,
        workoutExerciseId = workoutExerciseId,
        index = index,
        reps = reps,
        weight = weight,
        progress = status
    )



