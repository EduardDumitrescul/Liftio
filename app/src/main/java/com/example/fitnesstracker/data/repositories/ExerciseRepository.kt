package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.models.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getExercises(): List<Exercise>

    suspend fun add(exercise: Exercise): Int
}