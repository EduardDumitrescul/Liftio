package com.example.fitnesstracker.ui.components.exerciseCard.setRow

import com.example.fitnesstracker.data.models.ExerciseSet

data class SetState(
    val id: Int,
    val workoutExerciseId: Int,
    val index: Int,
    val reps: Int,
    val weight: Int,
    var style: SetRowStyle
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

fun ExerciseSet.toSetState(style: SetRowStyle = SetRowStyle.NORMAL) =
    SetState(
        id = id,
        workoutExerciseId = workoutExerciseId,
        index = index,
        reps = reps,
        weight = weight,
        style = style
    )



enum class SetRowStyle {
    NORMAL,
    HIGHLIGHTED,
    DISABLED
}