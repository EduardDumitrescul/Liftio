package com.thinkerbyte.fitnesstracker.data.roomdb.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.thinkerbyte.fitnesstracker.data.models.WorkoutExerciseCrossRef

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
data class WorkoutExerciseCrossRefEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val workoutId: Int,
    val exerciseId: Int,
    val index: Int
) {
    companion object {
        fun default() = WorkoutExerciseCrossRefEntity(
            0, 0, 0, 0
        )
    }

    fun toModel(): WorkoutExerciseCrossRef = WorkoutExerciseCrossRef(
        id = this.id,
        workoutId = this.workoutId,
        exerciseId = this.exerciseId,
        index = this.index,
    )
}

fun WorkoutExerciseCrossRef.toEntity() = WorkoutExerciseCrossRefEntity(
    id = this.id,
    workoutId = this.workoutId,
    exerciseId = this.exerciseId,
    index = this.index,
)