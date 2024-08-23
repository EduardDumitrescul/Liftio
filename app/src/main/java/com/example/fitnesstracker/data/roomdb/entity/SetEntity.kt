package com.example.fitnesstracker.data.roomdb.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.fitnesstracker.data.models.ExerciseSet

@Entity(
    tableName = "sets",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutExerciseCrossRefEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutExerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SetEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val workoutExerciseId: Int,
    val index: Int,
    val reps: Int,
    val weight: Int
)

fun SetEntity.toModel(): ExerciseSet = ExerciseSet(
    id = id,
    workoutExerciseId = workoutExerciseId,
    index = index,
    reps = reps,
    weight =  weight,
)

fun ExerciseSet.toEntity(): SetEntity = SetEntity(
    id = id,
    workoutExerciseId = workoutExerciseId,
    index = index,
    reps = reps,
    weight =  weight
)