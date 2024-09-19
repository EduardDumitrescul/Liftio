package com.thinkerbyte.fitnesstracker.data.dto

import java.time.LocalDateTime

data class WorkoutEntry(
    val id: Int,
    val name: String,
    val timeStarted: LocalDateTime,
    val duration: Long,
    val exercisesWithSets: List<ExerciseWithSets>
)