package com.example.fitnesstracker.data.roomdb.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "workoutExerciseCrossRefs",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class WorkoutExerciseCrossRef (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val workoutId: Int,
    val exerciseId: Int,
    val index: Int
) {
    companion object {
        fun default() = WorkoutExerciseCrossRef(
            0, 0, 0, 0
        )
    }
}