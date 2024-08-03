package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.dto.DetailedExercise
import com.example.fitnesstracker.data.models.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getBaseTemplates(): Flow<List<Workout>>
    fun getWorkout(workoutId: Int): Flow<Workout>
    suspend fun addExerciseToWorkout(workoutId: Int, exerciseId: Int): Int
    fun getExercisesWithSetsAndMuscles(workoutId: Int): Flow<List<DetailedExercise>>
    suspend fun updateTemplateName(templateId: Int, templateName: String)
    suspend fun removeWorkoutExerciseCrossRef(workoutExerciseCrossRefId: Int)
    suspend fun addWorkout(workout: Workout): Int
    suspend fun removeWorkout(workoutId: Int)
}