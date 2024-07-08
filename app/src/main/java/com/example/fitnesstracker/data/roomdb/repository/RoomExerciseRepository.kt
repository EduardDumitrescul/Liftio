package com.example.fitnesstracker.data.roomdb.repository

import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.data.repositories.ExerciseRepository
import com.example.fitnesstracker.data.roomdb.dao.ExerciseDao
import com.example.fitnesstracker.data.roomdb.dao.MuscleDao
import com.example.fitnesstracker.data.roomdb.entity.toEntity
import com.example.fitnesstracker.data.roomdb.entity.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.Console
import javax.inject.Inject

class RoomExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val muscleDao: MuscleDao,
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

    override suspend fun getExerciseWithMuscles(exerciseId: Int): ExerciseWithMuscles {
        val exercise = exerciseDao.getExerciseById(exerciseId).toModel()
        val primaryMuscle = muscleDao.getPrimaryMuscleByExerciseId(exerciseId)?.toModel()
        val secondaryMuscles = muscleDao.getSecondaryMusclesByExerciseId(exerciseId).map{ it.toModel()}

        return ExerciseWithMuscles(
            exercise,
            primaryMuscle!!.name,
            secondaryMuscles.map { it.name }
        )
    }

    override suspend fun updateExercise(exercise: Exercise) {
        val entity = exercise.toEntity()
        exerciseDao.updateExercise(entity)
    }
}