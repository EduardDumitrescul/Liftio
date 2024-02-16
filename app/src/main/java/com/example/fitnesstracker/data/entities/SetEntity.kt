package com.example.fitnesstracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets")
data class SetEntity (
    @PrimaryKey val id: Int,
    val templateExerciseId: Int,
    val index: Int,
    val reps: Int,
    val weight: Float,
)