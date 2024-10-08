package com.thinkerbyte.fitnesstracker.data.repositories

import com.thinkerbyte.fitnesstracker.data.models.ExerciseMuscleCrossRef
import com.thinkerbyte.fitnesstracker.data.models.Muscle
import kotlinx.coroutines.flow.Flow

interface MuscleRepository {
    fun getMuscleNames(): Flow<List<String>>
    fun getMuscleById(id: Int): Flow<Muscle?>
    fun getPrimaryMuscleByExerciseId(id: Int): Flow<Muscle?>
    fun getSecondaryMusclesByExerciseId(id: Int): Flow<List<Muscle>>
    suspend fun getMuscleId(name: String): Int
    suspend fun addExerciseMuscleCrossRef(exerciseMuscleCrossRef: ExerciseMuscleCrossRef)
    suspend fun removeExerciseMuscleRefs(exerciseId: Int)
    fun getMusclesByWorkoutId(id: Int): Flow<List<Muscle>>
    fun getMuscles(): Flow<List<Muscle>>
}