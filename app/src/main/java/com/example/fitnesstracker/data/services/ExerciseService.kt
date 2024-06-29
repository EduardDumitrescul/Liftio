package com.example.fitnesstracker.data.services

import com.example.fitnesstracker.data.dto.ExerciseSummary
import com.example.fitnesstracker.data.repositories.ExerciseRepository
import com.example.fitnesstracker.data.repositories.MuscleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class ExerciseService @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val muscleRepository: MuscleRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getExerciseSummaries(exerciseName: String = ""): Flow<List<ExerciseSummary>> {
        val exercises = exerciseRepository.getExercises()

        return exercises.flatMapConcat { exerciseList ->
            val filteredExerciseList = exerciseList.filter {
                exerciseName in it.name
            }
            val summaryFlows = filteredExerciseList.map { exercise ->
                combine(
                    muscleRepository.getPrimaryMuscleByExerciseId(exercise.id),
                    muscleRepository.getSecondaryMusclesByExerciseId(exercise.id)
                ) { primaryMuscle, secondaryMuscles ->
                    ExerciseSummary(
                        exercise.equipment,
                        exercise.name,
                        primaryMuscle?.name ?: "",
                        secondaryMuscles.map { it.name }
                    )
                }
            }

            combine(summaryFlows) { summariesArray ->
                summariesArray.toList()
            }
        }
    }
}