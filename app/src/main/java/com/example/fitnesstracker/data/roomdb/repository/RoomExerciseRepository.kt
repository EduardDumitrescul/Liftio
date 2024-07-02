package com.example.fitnesstracker.data.roomdb.repository

import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.data.repositories.ExerciseRepository
import com.example.fitnesstracker.data.roomdb.dao.ExerciseDao
import com.example.fitnesstracker.data.roomdb.entity.toEntity
import com.example.fitnesstracker.data.roomdb.entity.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RoomExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao
): ExerciseRepository {

    override suspend fun getExercises(): List<Exercise> {
        val entities = exerciseDao.getExercises()
        return entities.map {
            it.toModel()
        }
    }

    override suspend fun add(exercise: Exercise): Int {
        val entity = exercise.toEntity()
        return runBlocking {
            exerciseDao.addExercise(entity).toInt()
        }
    }
}