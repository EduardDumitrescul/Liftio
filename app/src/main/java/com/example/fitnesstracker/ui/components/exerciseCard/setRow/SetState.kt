package com.example.fitnesstracker.ui.components.exerciseCard.setRow

import com.example.fitnesstracker.data.models.ExerciseSet

data class SetState(
    val id: Int,
    val workoutExerciseId: Int,
    val index: Int,
    val reps: Int,
    val weight: Int,
    var status: SetStatus
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

fun ExerciseSet.toSetState(status: SetStatus = SetStatus.DONE) =
    SetState(
        id = id,
        workoutExerciseId = workoutExerciseId,
        index = index,
        reps = reps,
        weight = weight,
        status = status
    )



enum class SetStatus {
    DONE,
    ONGOING,
    TODO,
}