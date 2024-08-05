package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.dto.DetailedExercise
import com.example.fitnesstracker.data.models.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getTemplates(): Flow<List<Workout>>
    fun getWorkout(workoutId: Int): Flow<Workout>
    suspend fun addExerciseToWorkout(workoutId: Int, exerciseId: Int): Int
    fun getExercisesWithSetsAndMuscles(workoutId: Int): Flow<List<DetailedExercise>>
    suspend fun updateWorkoutName(workoutId: Int, name: String)
    suspend fun removeWorkoutExerciseCrossRef(workoutExerciseCrossRefId: Int)
    suspend fun addWorkout(workout: Workout): Int
    suspend fun removeWorkout(workoutId: Int)
}