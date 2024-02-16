package com.example.fitnesstracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscles")
data class MuscleEntity (
    @PrimaryKey val id: Int,
    val name: String,
)