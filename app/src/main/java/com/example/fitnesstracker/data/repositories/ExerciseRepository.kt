package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.models.Exercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

class ExerciseRepository {
    private var exercises = flowOf(listOf(
        Exercise(1, "Bench Press", "barbell"),
        Exercise(2, "Bicep Curl", "dumbbells"),
        Exercise(3, "Deadlift", "barbell"),
    ))

    fun getExercises(): Flow<List<Exercise>> {
        return exercises
    }
}