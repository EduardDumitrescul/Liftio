package com.thinkerbyte.fitnesstracker.data.roomdb.repository

import com.thinkerbyte.fitnesstracker.data.dto.ExerciseWithMuscles
import com.thinkerbyte.fitnesstracker.data.dto.ExerciseWithSets
import com.thinkerbyte.fitnesstracker.data.models.Exercise
import com.thinkerbyte.fitnesstracker.data.models.Muscle
import com.thinkerbyte.fitnesstracker.data.repositories.ExerciseRepository
import com.thinkerbyte.fitnesstracker.data.roomdb.dao.ExerciseDao
import com.thinkerbyte.fitnesstracker.data.roomdb.dao.MuscleDao
import com.thinkerbyte.fitnesstracker.data.roomdb.dao.SetDao
import com.thinkerbyte.fitnesstracker.data.roomdb.entity.ExerciseEntity
import com.thinkerbyte.fitnesstracker.data.roomdb.entity.WorkoutExerciseCrossRefEntity
import com.thinkerbyte.fitnesstracker.data.roomdb.entity.toEntity
import com.thinkerbyte.fitnesstracker.data.roomdb.entity.toModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RoomExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val muscleDao: MuscleDao,
    private val setDao: SetDao,
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
                exercise = ex.toModel(),
                primaryMuscle = pm?.toModel() ?: Muscle.default(),
                secondaryMuscles = sm.map { it.toModel() }
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
                    getWorkedMuscles(ex)
                }
                combine(combinedFlows) {it.toList()}
            }

        return exercisesWithMuscle
    }

    private fun getWorkedMuscles(ex: ExerciseEntity): Flow<ExerciseWithMuscles> {
        val primaryMuscle = muscleDao.getPrimaryMuscleByExerciseId(ex.id)
        val secondaryMuscles = muscleDao.getSecondaryMusclesByExerciseId(ex.id)
        return combine(
            primaryMuscle,
            secondaryMuscles
        ) { pm, sm ->
            ExerciseWithMuscles(
                exercise = ex.toModel(),
                primaryMuscle = pm?.toModel() ?: Muscle.default(),
                secondaryMuscles = sm.map { it.toModel() }
            )
        }
    }

    override suspend fun updateExercise(exercise: Exercise) {
        val entity = exercise.toEntity()
        exerciseDao.updateExercise(entity)
    }

    override fun getExercisesByWorkoutId(id: Int): Flow<List<Exercise>> {
        val exercises = exerciseDao.getExercisesByWorkoutId(id)
        return exercises.map { list ->
            list.map {
                it.toModel()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getExercisesWithSetsByWorkoutId(id: Int): Flow<List<ExerciseWithSets>> {
        return exerciseDao.getWorkoutExerciseCrossRefs(id).flatMapLatest { workoutExerciseCrossRefs ->
            if (workoutExerciseCrossRefs.isEmpty()) {
                flowOf(emptyList())
            } else {
                val combinedFlows = workoutExerciseCrossRefs.map { workoutExerciseCrossRef ->
                    getExerciseWithSets(workoutExerciseCrossRef)
                }

                combine(combinedFlows) { exerciseWithSetsList ->
                    val result = exerciseWithSetsList.toList()
                    result
                }
            }
        }
    }

    override fun getExerciseById(id: Int): Flow<Exercise> {
        return exerciseDao.getExerciseById(id).map { it.toModel() }
    }

    private fun getExerciseWithSets(workoutExerciseCrossRef: WorkoutExerciseCrossRefEntity): Flow<ExerciseWithSets> {
        val exerciseFlow = exerciseDao.getExerciseById(workoutExerciseCrossRef.exerciseId)
        val setsFlow = setDao.getSetsFlowByWorkoutExercise(workoutExerciseCrossRef.id)

        return combine(exerciseFlow, setsFlow) { exercise, sets ->
            ExerciseWithSets(
                exercise = exercise.toModel(),
                sets = sets.map { it.toModel() }
            )
        }
    }
}