package com.thinkerbyte.fitnesstracker.services

import com.thinkerbyte.fitnesstracker.data.dto.DetailedExercise
import com.thinkerbyte.fitnesstracker.data.dto.DetailedWorkout
import com.thinkerbyte.fitnesstracker.data.repositories.SetRepository
import com.thinkerbyte.fitnesstracker.data.repositories.WorkoutRepository
import kotlinx.coroutines.flow.first
import kotlin.math.min

private const val TAG = "TemplateUpdateHandler"

class TemplateUpdateHandler(
    private val detailedWorkout: DetailedWorkout,
    private val workoutRepository: WorkoutRepository,
    private val setRepository: SetRepository,
) {
    private lateinit var workoutExercises: List<DetailedExercise>
    private lateinit var templateExercises: List<DetailedExercise>

    private val exercisedUsed: MutableMap<Int, Boolean> = mutableMapOf()

    suspend fun run() {
        fetchData()
        updateTemplate()
    }

    private suspend fun fetchData() {
        workoutExercises = detailedWorkout.detailedExercises
        templateExercises = workoutRepository.getExercisesWithSetsAndMuscles(detailedWorkout.parentTemplateId).first()
    }

    private suspend fun updateTemplate() {
        templateExercises.forEach { updateExercise(it) }
    }

    private suspend fun updateExercise(templateExercise: DetailedExercise) {
        val detailedExercise: DetailedExercise? = findCorrespondingExercise(templateExercise)
        detailedExercise?.let {

            for(setIndex in 0 until min(it.sets.size, templateExercise.sets.size)) {
                val templateSet = templateExercise.sets[setIndex]
                val exerciseSet = it.sets[setIndex]
                if(templateSet.weight < exerciseSet.weight) {
                    val updatedSet = templateSet.copy(
                        weight = exerciseSet.weight,
                        reps = exerciseSet.reps
                    )
                    setRepository.updateSet(updatedSet)
                }
                else if(templateSet.weight == exerciseSet.weight && templateSet.reps < exerciseSet.reps) {
                    val updatedSet = templateSet.copy(
                        reps = exerciseSet.reps
                    )
                    setRepository.updateSet(updatedSet)
                }
            }

            for(setIndex in templateExercise.sets.size until it.sets.size) {
                val exerciseSet = it.sets[setIndex]
                val newSet = exerciseSet.copy(
                    id = 0,
                    workoutExerciseId = templateExercise.templateExerciseCrossRefId,
                    weight = exerciseSet.weight,
                    reps = exerciseSet.reps,
                    index = exerciseSet.index
                )
                setRepository.addSet(newSet)
            }
        }
    }

    private fun findCorrespondingExercise(templateExercise: DetailedExercise): DetailedExercise? {
        return workoutExercises.find {
            it.exercise.id == templateExercise.exercise.id
                    && exercisedUsed.getOrPut(it.exercise.id) { true }
        }
    }
}