package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.models.ExerciseMuscleCrossRef
import com.example.fitnesstracker.data.models.Muscle
import kotlinx.coroutines.flow.Flow

interface MuscleRepository {
    suspend fun getMuscleNames(): List<String>
    suspend fun getMuscleById(id: Int): Muscle?
    suspend fun getPrimaryMuscleByExerciseId(id: Int): Muscle?
    suspend fun getSecondaryMusclesByExerciseId(id: Int): List<Muscle>
    suspend fun getMuscleId(name: String): Int
    suspend fun addExerciseMuscleCrossRef(exerciseMuscleCrossRef: ExerciseMuscleCrossRef)
    suspend fun removeExerciseMuscleRefs(exerciseId: Int)
}