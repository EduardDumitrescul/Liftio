package com.thinkerbyte.fitnesstracker.data.dto

import com.thinkerbyte.fitnesstracker.data.models.Workout
import java.time.LocalDateTime


data class DetailedWorkout(
    val id: Int,
    val parentTemplateId: Int,
    val name: String,
    val isBaseTemplate: Boolean,
    val detailedExercises: List<DetailedExercise>,
    val timeStarted: LocalDateTime,
    val duration: Long,
) {
    companion object {
        fun default() = DetailedWorkout(
            id = 0,
            parentTemplateId = 0,
            name = "Default Workout",
            isBaseTemplate = false,
            detailedExercises = emptyList(),
            timeStarted = LocalDateTime.now(),
            duration = 0,
        )
    }

    fun getWorkout() = Workout(
        id = id,
        parentTemplateId = parentTemplateId,
        name = name,
        isBaseTemplate = isBaseTemplate,
        timeStarted = timeStarted,
        duration = duration
    )
}