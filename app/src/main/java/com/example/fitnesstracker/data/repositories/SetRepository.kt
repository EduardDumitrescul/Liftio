package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.models.ExerciseSet

interface SetRepository {
    suspend fun insertSet(set: ExerciseSet)
    suspend fun removeSet(id: Int)
    suspend fun updateSetIndexes(templateExerciseCrossRefId: Int, indexToBeRemoved: Int)
    suspend fun getSet(id: Int): ExerciseSet
}