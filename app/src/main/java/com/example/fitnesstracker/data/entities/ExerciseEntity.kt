package com.example.fitnesstracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseEntity (
    @PrimaryKey val id: Int,
    val name: String,
    val description: String
)