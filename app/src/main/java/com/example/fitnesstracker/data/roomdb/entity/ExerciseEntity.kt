package com.example.fitnesstracker.data.roomdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnesstracker.data.models.Exercise

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "equipment") val equipment: String,
)

fun ExerciseEntity.toModel(): Exercise =
    Exercise(
        id = this.id,
        name = this.name,
        description = this.description,
        equipment = this.equipment
    )

fun Exercise.toEntity(): ExerciseEntity =
    ExerciseEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        equipment = this.equipment
    )