package com.thinkerbyte.fitnesstracker.services

import android.util.Log
import com.thinkerbyte.fitnesstracker.data.dto.DetailedWorkout
import com.thinkerbyte.fitnesstracker.data.dto.WorkoutSummary
import com.thinkerbyte.fitnesstracker.data.models.ExerciseSet
import com.thinkerbyte.fitnesstracker.data.models.Workout
import com.thinkerbyte.fitnesstracker.data.repositories.ExerciseRepository
import com.thinkerbyte.fitnesstracker.data.repositories.MuscleRepository
import com.thinkerbyte.fitnesstracker.data.repositories.SessionRepository
import com.thinkerbyte.fitnesstracker.data.repositories.SetRepository
import com.thinkerbyte.fitnesstracker.data.repositories.WorkoutRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

private const val TAG = "WorkoutService"

class WorkoutService @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val setRepository: SetRepository,
    private val muscleRepository: MuscleRepository,
    private val exerciseRepository: ExerciseRepository,
    private val sessionRepository: SessionRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTemplateSummaries(): Flow<List<WorkoutSummary>> {
        return workoutRepository.getTemplates().flatMapLatest { templates ->
            if (templates.isEmpty()) {
                flowOf(emptyList())
            } else {
                val workoutSummaryFlows = templates.map { getWorkoutSummary(it.id) }

                combine(workoutSummaryFlows) {
                    val summaries = it.toList()
                    summaries
                }
            }
        }
    }

    private fun getWorkoutSummary(id: Int): Flow<WorkoutSummary> {
        val musclesFlow = muscleRepository.getMusclesByWorkoutId(id)
        val exercisesFlow = exerciseRepository.getExercisesWithSetsByWorkoutId(id)
        val workoutFlow = workoutRepository.getWorkout(id)

        return combine(workoutFlow, musclesFlow, exercisesFlow) { workout, muscles, exercises ->
            WorkoutSummary(
                id = workout.id,
                name = workout.name,
                workedMuscles = muscles.distinctBy { it.group }.map { it.group },
                exerciseList = exercises.map { "${it.sets.size} x ${it.exercise.name}" }
            )
        }
    }

    fun getDetailedWorkout(id: Int): Flow<DetailedWorkout> {
        val workoutFlow = workoutRepository.getWorkout(id)
        val exercises = workoutRepository.getExercisesWithSetsAndMuscles(id)

        return combine(
            workoutFlow,
            exercises
        ) { workout, ex ->
            DetailedWorkout(
                id = workout.id,
                parentTemplateId = workout.parentTemplateId,
                name = workout.name,
                isBaseTemplate =  workout.isBaseTemplate,
                detailedExercises = ex,
                timeStarted = workout.timeStarted,
                duration = workout.duration,

            )
        }
    }

    suspend fun addExerciseToTemplate(id: Int, exerciseId: Int) {
        workoutRepository.addExerciseToWorkout(id, exerciseId)
    }

    suspend fun updateTemplateName(id: Int, name: String) {
        workoutRepository.updateWorkoutName(id, name)
    }

    suspend fun removeExerciseFromWorkout(workoutExerciseCrossRefId: Int) {
        workoutRepository.removeWorkoutExerciseCrossRef(workoutExerciseCrossRefId)
    }

    suspend fun removeSetFromWorkoutExercise(setId: Int) {
        try {
            val set = setRepository.getSet(setId)
            setRepository.updateSetIndexes(set.workoutExerciseId, set.index)
            setRepository.removeSet(setId)
        }
        catch (ignored: Exception) {

        }
    }

    suspend fun addSetToWorkoutExercise(workoutExerciseCrossRefId: Int) {
        val sets: List<ExerciseSet> = setRepository.getSetsForWorkoutExercise(workoutExerciseCrossRefId)

        val newSet =
            if(sets.isEmpty())
                ExerciseSet(0, workoutExerciseCrossRefId, 1, 12, 20)
            else
                sets.last().copy(
                    id = 0,
                    index = sets.last().index + 1,
                )


        setRepository.addSet(newSet)
    }

    suspend fun updateSet(set: ExerciseSet) {
        setRepository.updateSet(set)
    }

    suspend fun createNewTemplate(): Int {
        val workout = Workout(
            id = 0,
            parentTemplateId = 0,
            name = "New Template",
            isBaseTemplate = true,
            timeStarted = LocalDateTime.now(),
            duration = 0,
        )
        val id = workoutRepository.addWorkout(workout)
        return id
    }

    suspend fun removeTemplate(templateId: Int) {
        workoutRepository.removeWorkout(templateId)
    }

    suspend fun createWorkoutFromTemplate(detailedTemplate: DetailedWorkout): Int {
        val workout = Workout(
            id = 0,
            parentTemplateId = detailedTemplate.id,
            name = detailedTemplate.name,
            isBaseTemplate = false,
            timeStarted = LocalDateTime.now(),
            duration = 0,
        )

        val workoutId = workoutRepository.addWorkout(workout)

        for(detailedExercise in detailedTemplate.detailedExercises) {
            val workoutExerciseCrossRefId = workoutRepository.addExerciseToWorkout(workoutId, detailedExercise.exercise.id)
            for(set in detailedExercise.sets) {
                val exerciseSet = ExerciseSet(
                    id = 0,
                    workoutExerciseId = workoutExerciseCrossRefId,
                    index = set.index,
                    reps = set.reps,
                    weight = set.weight
                )
                setRepository.addSet(exerciseSet)
            }
        }

        sessionRepository.updateOngoingWorkout(workoutId)
        return workoutId
    }

    suspend fun finishWorkout(workoutId: Int) {
        val detailedWorkout = getDetailedWorkout(workoutId).first()
        if(!detailedWorkout.isBaseTemplate) {
            TemplateUpdateHandler(
                detailedWorkout = detailedWorkout,
                workoutRepository = workoutRepository,
                setRepository = setRepository
            ).run()
        }
        val workout = detailedWorkout.getWorkout()
        val updatedWorkout = workout.copy(
            duration = Duration.between(workout.timeStarted, LocalDateTime.now()).seconds
        )

        workoutRepository.updateWorkout(updatedWorkout)
        sessionRepository.removeOngoingWorkout()
    }

    suspend fun createBlankWorkout(): Int {
        val workout = Workout.default()
        val id = workoutRepository.addWorkout(workout)
        sessionRepository.updateOngoingWorkout(id)
        return id
    }

    suspend fun updateWorkoutExerciseIndexes(newIndexesForId: List<Pair<Int, Int>>) {
        workoutRepository.updateWorkoutExerciseIndexes(newIndexesForId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getOngoingWorkout(): Flow<Workout?> {
        val temp = sessionRepository.getSessionPreferences()
            .flatMapLatest { sessionPreferences ->
                if (!sessionPreferences.exists) {
                    Log.d(TAG, "null")
                    flow { emit(null) }
                }
                else{
                    workoutRepository.getWorkout(sessionPreferences.workoutId) as Flow<Workout?>
                }
            }
        return temp
    }
}