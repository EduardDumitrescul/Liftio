package com.thinkerbyte.fitnesstracker.data.models

data class ExerciseMuscleCrossRef(
    val exerciseId: Int,
    val muscleId: Int,
    val isPrimary: Boolean,
)