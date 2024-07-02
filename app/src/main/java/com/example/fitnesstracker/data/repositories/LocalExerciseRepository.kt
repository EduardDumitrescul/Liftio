package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.data.models.Exercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

//class LocalExerciseRepository: ExerciseRepository {
//    private val _exercises =
//        mutableListOf(
//            Exercise(1, "Bench Press", "The basic Chest Exercise", "barbell"),
//            Exercise(2, "Bicep Curl", "Gym Bro's favorite", "dumbbells"),
//            Exercise(3, "Deadlift", "Do at your own risk", "barbell")
//        )
//    private var index = 4
//
//    override suspend fun getExercises(): List<Exercise> {
//        return _exercises
//    }
//
//    override suspend fun add(exercise: Exercise): Int {
//        _exercises.add(exercise.copy(id = index ++))
//        return index - 1
//    }
//
//    override suspend fun getExerciseWithMuscles(exerciseId: Int): ExerciseWithMuscles {
//
//    }
//}