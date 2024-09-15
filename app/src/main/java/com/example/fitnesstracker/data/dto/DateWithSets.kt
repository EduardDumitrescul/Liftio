package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.data.models.ExerciseSet
import java.time.LocalDateTime

data class DateWithSets(
    val sets: List<ExerciseSet>,
    val date: LocalDateTime,
) {
}