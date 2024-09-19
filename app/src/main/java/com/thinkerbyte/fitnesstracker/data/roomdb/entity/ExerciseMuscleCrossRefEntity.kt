package com.thinkerbyte.fitnesstracker.data.roomdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.thinkerbyte.fitnesstracker.data.models.ExerciseMuscleCrossRef

@Entity(primaryKeys = ["exerciseId", "muscleId"], tableName = "exerciseMuscleCrossRef")
data class ExerciseMuscleCrossRefEntity(
    @ColumnInfo(name = "exerciseId") val exerciseId: Int,
    @ColumnInfo(name = "muscleId") val muscleId: Int,
    @ColumnInfo(name = "isPrimary") val isPrimary: Boolean,
)

fun ExerciseMuscleCrossRefEntity.toModel() = ExerciseMuscleCrossRef(
    exerciseId = exerciseId,
    muscleId = muscleId,
    isPrimary = isPrimary
)

fun ExerciseMuscleCrossRef.toEntity() = ExerciseMuscleCrossRefEntity(
    exerciseId = exerciseId,
    muscleId = muscleId,
    isPrimary = isPrimary
)