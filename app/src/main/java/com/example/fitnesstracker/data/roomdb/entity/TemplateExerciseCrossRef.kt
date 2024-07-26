package com.example.fitnesstracker.data.roomdb.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "templateExerciseCrossRefs",
    foreignKeys = [
        ForeignKey(
            entity = TemplateEntity::class,
            parentColumns = ["id"],
            childColumns = ["templateId"],
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
data class TemplateExerciseCrossRef (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val templateId: Int,
    val exerciseId: Int,
    val index: Int
)