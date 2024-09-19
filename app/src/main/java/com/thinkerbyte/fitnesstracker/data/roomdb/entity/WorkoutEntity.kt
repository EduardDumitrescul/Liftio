package com.thinkerbyte.fitnesstracker.data.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thinkerbyte.fitnesstracker.data.models.Workout
import java.time.LocalDateTime

@Entity(tableName = "workouts")
data class WorkoutEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val parentTemplateId: Int,
    val name: String,
    val isTemplate: Boolean,
    val timeStarted: LocalDateTime,
    val duration: Long,
)

fun WorkoutEntity.toModel() = Workout(
    id = id,
    parentTemplateId = parentTemplateId,
    name = name,
    isBaseTemplate = isTemplate,
    timeStarted = timeStarted,
    duration = duration,
)

fun Workout.toEntity() = WorkoutEntity(
    id = id,
    parentTemplateId = parentTemplateId,
    name = name,
    isTemplate = isBaseTemplate,
    timeStarted = timeStarted,
    duration = duration
)