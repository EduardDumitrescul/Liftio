package com.example.fitnesstracker.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["exerciseId", "muscleId"])
data class ExerciseMuscleCrossRefEntity(
    @ColumnInfo(name = "exerciseId") val exerciseId: Int,
    @ColumnInfo(name = "muscleId") val muscleID: Int,
    @ColumnInfo(name = "isPrimary") val isPrimary: Boolean,
)