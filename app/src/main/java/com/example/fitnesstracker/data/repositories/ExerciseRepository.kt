package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.data.dto.ExerciseWithSets
import com.example.fitnesstracker.data.dto.ExerciseDetailed
import com.example.fitnesstracker.data.models.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getExercises(): Flow<List<Exercise>>

    suspend fun add(exercise: Exercise): Int
    fun getExerciseWithMuscles(exerciseId: Int): Flow<ExerciseWithMuscles>
    fun getExercisesWithMuscles(): Flow<List<ExerciseWithMuscles>>
    suspend fun updateExercise(exercise: Exercise)
    fun getExercisesByTemplateId(id: Int): Flow<List<Exercise>>
    fun getExercisesWithSetsByTemplateId(id: Int): Flow<List<ExerciseWithSets>>
}