package com.thinkerbyte.fitnesstracker.data.repositories

import com.thinkerbyte.fitnesstracker.data.dto.ExerciseWithMuscles
import com.thinkerbyte.fitnesstracker.data.dto.ExerciseWithSets
import com.thinkerbyte.fitnesstracker.data.models.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getExercises(): Flow<List<Exercise>>

    suspend fun add(exercise: Exercise): Int
    fun getExerciseWithMuscles(exerciseId: Int): Flow<ExerciseWithMuscles>
    fun getExercisesWithMuscles(): Flow<List<ExerciseWithMuscles>>
    suspend fun updateExercise(exercise: Exercise)
    fun getExercisesByWorkoutId(id: Int): Flow<List<Exercise>>
    fun getExercisesWithSetsByWorkoutId(id: Int): Flow<List<ExerciseWithSets>>
    fun getExerciseById(id: Int): Flow<Exercise>
}