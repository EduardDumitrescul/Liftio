package com.example.fitnesstracker.services

import android.util.Log
import com.example.fitnesstracker.data.dto.DetailedWorkout
import com.example.fitnesstracker.data.dto.WorkoutSummary
import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.data.models.Workout
import com.example.fitnesstracker.data.repositories.ExerciseRepository
import com.example.fitnesstracker.data.repositories.MuscleRepository
import com.example.fitnesstracker.data.repositories.SetRepository
import com.example.fitnesstracker.data.repositories.WorkoutRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

private const val TAG = "TemplateService"

class WorkoutService @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val setRepository: SetRepository,
    private val muscleRepository: MuscleRepository,
    private val exerciseRepository: ExerciseRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTemplateSummaries(): Flow<List<WorkoutSummary>> {
        return workoutRepository.getTemplates().flatMapLatest { templates ->
            if (templates.isEmpty()) {
                Log.d(TAG, "No templates found.")
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
                workedMuscles = muscles.map { it.name },
                exerciseList = exercises.map { "${it.sets.size} x ${it.exercise.name}" }
            )
        }
    }

    fun getDetailedWorkout(id: Int): Flow<DetailedWorkout> {
        val templateFlow = workoutRepository.getWorkout(id)
        val exercises = workoutRepository.getExercisesWithSetsAndMuscles(id)

        return combine(
            templateFlow,
            exercises
        ) { temp, ex ->
            DetailedWorkout(
                id = temp.id,
                parentTemplateId = temp.parentTemplateId,
                name = temp.name,
                isBaseTemplate =  temp.isBaseTemplate,
                exercisesWithSetsAndMuscles = ex
            )
        }
    }

    suspend fun addExerciseToTemplate(id: Int, exerciseId: Int) {
        workoutRepository.addExerciseToWorkout(id, exerciseId)
    }

    suspend fun updateTemplateName(id: Int, name: String) {
        workoutRepository.updateWorkoutName(id, name)
    }

    suspend fun removeExerciseFromTemplate(workoutExerciseCrossRefId: Int) {
        workoutRepository.removeWorkoutExerciseCrossRef(workoutExerciseCrossRefId)
    }

    suspend fun removeSetFromWorkoutExercise(setId: Int) {
        val set = setRepository.getSet(setId)
        setRepository.updateSetIndexes(set.workoutExerciseId, set.index)
        setRepository.removeSet(setId)
    }

    suspend fun addSetToWorkoutExercise(workoutExerciseCrossRefId: Int) {
        val sets: List<ExerciseSet> = setRepository.getSetsForWorkoutExercise(workoutExerciseCrossRefId)

        val newSet =
            if(sets.isEmpty())
                ExerciseSet(0, workoutExerciseCrossRefId, 1, 12, 20)
            else
                sets.last().copy(
                    id = 0,
                    index = sets.last().index + 1
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
            isBaseTemplate = true
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
            isBaseTemplate = false
        )

        val workoutId = workoutRepository.addWorkout(workout)

        for(detailedExercise in detailedTemplate.exercisesWithSetsAndMuscles) {
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

        return workoutId
    }
}