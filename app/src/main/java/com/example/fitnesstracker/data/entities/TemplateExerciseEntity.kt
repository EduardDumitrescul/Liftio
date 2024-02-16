package com.example.fitnesstracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templateExercises")
data class TemplateExerciseEntity (
    @PrimaryKey val id: Int,
    val templateId: Int,
    val exerciseId: Int,
    val index: Int,
)