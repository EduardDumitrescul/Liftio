package com.example.fitnesstracker.data.roomdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnesstracker.data.models.Muscle

@Entity(tableName = "muscles")
data class MuscleEntity(
    @PrimaryKey(autoGenerate = true)  val id: Int,
    @ColumnInfo(name = "name") val name: String,
)

fun MuscleEntity.toModel(): Muscle =
    Muscle(
        id = id,
        name = name,
    )

fun Muscle.toEntity(): MuscleEntity =
    MuscleEntity(
        id = id,
        name = name,
    )