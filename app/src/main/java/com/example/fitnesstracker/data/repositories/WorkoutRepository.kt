package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.dto.DetailedExercise
import com.example.fitnesstracker.data.models.Workout
import com.example.fitnesstracker.data.models.WorkoutExerciseCrossRef
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

interface WorkoutRepository {
    fun getTemplates(): Flow<List<Workout>>
    fun getWorkout(workoutId: Int): Flow<Workout>
    suspend fun addExerciseToWorkout(workoutId: Int, exerciseId: Int): Int
    fun getExercisesWithSetsAndMuscles(workoutId: Int): Flow<List<DetailedExercise>>
    suspend fun updateWorkoutName(workoutId: Int, name: String)
    suspend fun removeWorkoutExerciseCrossRef(workoutExerciseCrossRefId: Int)
    suspend fun addWorkout(workout: Workout): Int
    suspend fun removeWorkout(workoutId: Int)
    suspend fun updateWorkout(workout: Workout)
    fun getAllWorkoutEntries(): Flow<List<Workout>>
    suspend fun getWorkoutExercise(workoutExerciseId: Int): WorkoutExerciseCrossRef
    suspend fun updateWorkoutExerciseIndexes(newIndexesForId: List<Pair<Int, Int>>)
    fun getNumberOfWorkoutsCompleted(): Flow<Int>
    fun getTimeTrained(): Flow<Int>
    fun getSetsCompleted(): Flow<Int>
    fun getWorkoutDates(): Flow<List<LocalDateTime>>
}