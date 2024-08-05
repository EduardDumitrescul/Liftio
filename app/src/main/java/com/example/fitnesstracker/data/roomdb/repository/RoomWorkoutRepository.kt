package com.example.fitnesstracker.data.roomdb.repository

import com.example.fitnesstracker.data.dto.DetailedExercise
import com.example.fitnesstracker.data.models.Workout
import com.example.fitnesstracker.data.repositories.WorkoutRepository
import com.example.fitnesstracker.data.roomdb.dao.ExerciseDao
import com.example.fitnesstracker.data.roomdb.dao.MuscleDao
import com.example.fitnesstracker.data.roomdb.dao.SetDao
import com.example.fitnesstracker.data.roomdb.dao.WorkoutDao
import com.example.fitnesstracker.data.roomdb.entity.WorkoutExerciseCrossRef
import com.example.fitnesstracker.data.roomdb.entity.toEntity
import com.example.fitnesstracker.data.roomdb.entity.toModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "RoomTemplateRepository"

class RoomWorkoutRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val muscleDao: MuscleDao,
    private val setDao: SetDao,
    private val workoutDao: WorkoutDao,
): WorkoutRepository {
    override fun getTemplates(): Flow<List<Workout>> {
        val entities = workoutDao.getTemplates()
        return entities.map { list ->
            list.map {
                it.toModel()
            }
        }
    }

    override fun getWorkout(workoutId: Int): Flow<Workout> {
        val templateEntity = workoutDao.getWorkoutById(workoutId)
        return templateEntity.map {
            it?.toModel() ?: Workout.default()
        }
    }

    override suspend fun addExerciseToWorkout(workoutId: Int, exerciseId: Int): Int {
        val numberOfExercisesInTemplate = workoutDao.getNumberOfExercisesInWorkout(workoutId)
        val templateExercise = WorkoutExerciseCrossRef(
            id = 0,
            workoutId = workoutId,
            exerciseId = exerciseId,
            index = numberOfExercisesInTemplate + 1
        )
        return workoutDao.insertWorkoutExerciseCrossRef(templateExercise).toInt()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getExercisesWithSetsAndMuscles(workoutId: Int): Flow<List<DetailedExercise>> {
        val workoutExerciseFlow = workoutDao.getWorkoutExercisesByTemplateId(workoutId)

        return workoutExerciseFlow.flatMapLatest { workoutExercises ->
            if (workoutExercises.isEmpty()) {
                flowOf(emptyList())
            } else {
                val combinedFlows = workoutExercises.map { workoutExerciseCrossRef ->
                    getDetailedExercise(workoutExerciseCrossRef)
                }

                combine(combinedFlows) { it.toList() }
            }
        }
    }

    private fun getDetailedExercise(workoutExerciseCrossRef: WorkoutExerciseCrossRef): Flow<DetailedExercise> {
        val exerciseFlow = exerciseDao.getExerciseById(workoutExerciseCrossRef.exerciseId)
        val primaryMuscleFlow = muscleDao.getPrimaryMuscleByExerciseId(workoutExerciseCrossRef.exerciseId)
        val secondaryMusclesFlow = muscleDao.getSecondaryMusclesByExerciseId(workoutExerciseCrossRef.exerciseId)
        val setsFlow = setDao.getSetsFlowByWorkoutExercise(workoutExerciseCrossRef.id)

        return combine(
            exerciseFlow,
            primaryMuscleFlow,
            secondaryMusclesFlow,
            setsFlow
        ) { exercise, primaryMuscle, secondaryMuscles, sets ->
            DetailedExercise(
                exercise = exercise.toModel(),
                templateExerciseCrossRefId = workoutExerciseCrossRef.id,
                primaryMuscle = primaryMuscle!!.toModel(),
                secondaryMuscles = secondaryMuscles.map { it.toModel() },
                sets = sets.map { it.toModel() }
            )
        }
    }

    override suspend fun updateWorkoutName(workoutId: Int, name: String) {
        workoutDao.updateWorkoutName(workoutId, name)
    }

    override suspend fun removeWorkoutExerciseCrossRef(workoutExerciseCrossRefId: Int) {
        workoutDao.removeWorkoutExerciseCrossRef(workoutExerciseCrossRefId)
    }

    override suspend fun addWorkout(workout: Workout): Int {
        val entity = workout.toEntity()
        return workoutDao.insert(entity).toInt()
    }

    override suspend fun removeWorkout(workoutId: Int) {
        workoutDao.removeWorkout(workoutId)
    }

}