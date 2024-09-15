package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.dto.DateWithSets
import com.example.fitnesstracker.data.models.ExerciseSet
import kotlinx.coroutines.flow.Flow

interface SetRepository {
    suspend fun insertSet(set: ExerciseSet)
    suspend fun removeSet(id: Int)
    suspend fun updateSetIndexes(workoutExerciseCrossRefId: Int, indexToBeRemoved: Int)
    suspend fun getSet(id: Int): ExerciseSet
    suspend fun getSetsForWorkoutExercise(workoutExerciseCrossRefId: Int): List<ExerciseSet>
    suspend fun addSet(set: ExerciseSet)
    suspend fun updateSet(set: ExerciseSet)
    fun getSetsHistory(exerciseId: Int): Flow<List<DateWithSets>>
}