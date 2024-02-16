package com.example.fitnesstracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "workouts")
data class WorkoutEntity (
    @PrimaryKey val id: Int,
    val templateId: Int,
    val startDate: LocalDate,
    val duration: Int,
)