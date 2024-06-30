package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.models.Exercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

class ExerciseRepository {
    private val _exercises = MutableStateFlow(
        mutableListOf(
            Exercise(1, "Bench Press", "The basic Chest Exercise", "barbell"),
            Exercise(2, "Bicep Curl", "Gym Bro's favorite", "dumbbells"),
            Exercise(3, "Deadlift", "Do at your own risk", "barbell")
        )
    )
    private var index = 4

    fun getExercises(): Flow<List<Exercise>> {
        return _exercises
    }

    fun add(exercise: Exercise): Int {
        _exercises.value.add(exercise.copy(id = index ++))
        return index - 1
    }
}