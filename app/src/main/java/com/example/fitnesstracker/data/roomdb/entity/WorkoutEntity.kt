package com.example.fitnesstracker.data.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnesstracker.data.models.Workout

@Entity(tableName = "workouts")
data class WorkoutEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val parentTemplateId: Int,
    val name: String,
    val isTemplate: Boolean
)

fun WorkoutEntity.toModel() = Workout(
    id = id,
    parentTemplateId = parentTemplateId,
    name = name,
    isBaseTemplate = isTemplate,
)

fun Workout.toEntity() = WorkoutEntity(
    id = id,
    parentTemplateId = parentTemplateId,
    name = name,
    isTemplate = isBaseTemplate
)