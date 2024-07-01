package com.example.fitnesstracker.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MuscleEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
)