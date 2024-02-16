package com.example.fitnesstracker.data.entities

import androidx.room.Entity

@Entity(tableName = "ExerciseMuscle", primaryKeys = ["exerciseId", "muscleId"])
data class ExerciseMuscleEntity(
    val exerciseId: Int,
    val muscleId: Int,
    val isPrimary: Boolean = false
)