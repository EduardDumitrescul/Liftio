package com.thinkerbyte.fitnesstracker.data.dto

import com.thinkerbyte.fitnesstracker.data.models.ExerciseSet
import java.time.LocalDateTime

data class DateWithSets(
    val sets: List<ExerciseSet>,
    val date: LocalDateTime,
)