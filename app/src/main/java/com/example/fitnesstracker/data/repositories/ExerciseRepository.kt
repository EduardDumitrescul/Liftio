package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.models.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getExercises(): Flow<List<Exercise>>

    fun add(exercise: Exercise): Int
}