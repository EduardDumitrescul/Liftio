package com.example.fitnesstracker.data.roomdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnesstracker.data.models.Muscle

@Entity(tableName = "muscles")
data class MuscleEntity(
    @PrimaryKey(autoGenerate = true)  val id: Int,
    @ColumnInfo(name = "group") val group: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "scientificName") val scientificName: String,
)

fun MuscleEntity.toModel(): Muscle =
    Muscle(
        id = id,
        name = name,
        group = group,
        scientificName = scientificName,
    )

fun Muscle.toEntity(): MuscleEntity =
    MuscleEntity(
        id = id,
        name = name,
        group = group,
        scientificName = scientificName,
    )