package com.example.fitnesstracker.data.roomdb.repository

import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.data.repositories.ExerciseRepository
import com.example.fitnesstracker.data.roomdb.dao.ExerciseDao
import com.example.fitnesstracker.data.roomdb.dao.MuscleDao
import com.example.fitnesstracker.data.roomdb.entity.ExerciseEntity
import com.example.fitnesstracker.data.roomdb.entity.toEntity
import com.example.fitnesstracker.data.roomdb.entity.toModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.Console
import javax.inject.Inject

class RoomExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val muscleDao: MuscleDao,
): ExerciseRepository {

    override fun getExercises(): Flow<List<Exercise>> {
        val entities = exerciseDao.getExercises()
        return entities.map { muscle ->
            muscle.map {
                it.toModel()
            }
        }
    }

    override suspend fun add(exercise: Exercise): Int {
        val entity = exercise.toEntity()
        return runBlocking {
            exerciseDao.addExercise(entity).toInt()
        }
    }

    override fun getExerciseWithMuscles(exerciseId: Int): Flow<ExerciseWithMuscles> {
        val exercise = exerciseDao.getExerciseById(exerciseId)
        val primaryMuscle = muscleDao.getPrimaryMuscleByExerciseId(exerciseId)
        val secondaryMuscles = muscleDao.getSecondaryMusclesByExerciseId(exerciseId)

        val exerciseWithMuscle: Flow<ExerciseWithMuscles> = combine(
            exercise,
            primaryMuscle,
            secondaryMuscles
        ) { ex, pm, sm ->
            ExerciseWithMuscles(
                exercise = ex?.toModel() ?: Exercise(0, "", "", ""),
                primaryMuscle = pm?.name ?: "",
                secondaryMuscles = sm.map { it.name }
            )
        }

        return exerciseWithMuscle
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getExercisesWithMuscles(): Flow<List<ExerciseWithMuscles>> {
        val exercises = exerciseDao.getExercises()

        val exercisesWithMuscle: Flow<List<ExerciseWithMuscles>> =
            exercises.flatMapLatest { exList ->
                val combinedFlows = exList.map { ex ->
                    val primaryMuscle = muscleDao.getPrimaryMuscleByExerciseId(ex.id)
                    val secondaryMuscles = muscleDao.getSecondaryMusclesByExerciseId(ex.id)
                    combine(
                        primaryMuscle,
                        secondaryMuscles
                    ) { pm, sm ->
                        ExerciseWithMuscles(
                            ex.toModel(),
                            pm?.name ?: "",
                            sm.map { it.name }
                        )
                    }
                }

                combine(combinedFlows) {it.toList()}
            }

        return exercisesWithMuscle
    }

    override suspend fun updateExercise(exercise: Exercise) {
        val entity = exercise.toEntity()
        exerciseDao.updateExercise(entity)
    }
}