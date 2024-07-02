package com.example.fitnesstracker.services

import com.example.fitnesstracker.data.dto.ExerciseSummary
import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.data.models.ExerciseMuscleCrossRef
import com.example.fitnesstracker.data.repositories.ExerciseRepository
import com.example.fitnesstracker.data.repositories.MuscleRepository
import javax.inject.Inject

private const val TAG = "ExerciseService"

class ExerciseService @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val muscleRepository: MuscleRepository
) {

    suspend fun getExerciseSummaries(exerciseName: String = ""): List<ExerciseSummary> {
        val exercises = exerciseRepository.getExercises()

        val filteredExerciseList = exercises.filter {
            exerciseName in it.name
        }
        val summaries = filteredExerciseList.map { exercise ->
            val primaryMuscle =  muscleRepository.getPrimaryMuscleByExerciseId(exercise.id)
            val secondaryMuscles = muscleRepository.getSecondaryMusclesByExerciseId(exercise.id)

            ExerciseSummary(
                exercise.id,
                exercise.equipment,
                exercise.name,
                primaryMuscle?.name ?: "",
                secondaryMuscles.map { it.name }
            )
        }

        return summaries
    }

    suspend fun add(exerciseWithMuscles: ExerciseWithMuscles) {
        val exercise = exerciseWithMuscles.getExercise()
        val exerciseId = exerciseRepository.add(exercise)

        val primaryMuscleId = muscleRepository.getMuscleId(exerciseWithMuscles.primaryMuscle)
        muscleRepository.addExerciseMuscleCrossRef(ExerciseMuscleCrossRef(exerciseId, primaryMuscleId, true))

        for(muscle in exerciseWithMuscles.secondaryMuscles) {
            val muscleId = muscleRepository.getMuscleId(muscle)
            muscleRepository.addExerciseMuscleCrossRef(ExerciseMuscleCrossRef(exerciseId, muscleId, false))
        }
    }

    suspend fun getExerciseWithMuscles(exerciseId: Int): ExerciseWithMuscles {
        return exerciseRepository.getExerciseWithMuscles(exerciseId)
    }

    suspend fun updateExercise(exerciseWithMuscles: ExerciseWithMuscles) {
        val exercise = exerciseWithMuscles.getExercise()
        exerciseRepository.updateExercise(exercise)
        muscleRepository.removeExerciseMuscleRefs(exercise.id)

        val primaryMuscleId = muscleRepository.getMuscleId(exerciseWithMuscles.primaryMuscle)
        muscleRepository.addExerciseMuscleCrossRef(ExerciseMuscleCrossRef(exercise.id, primaryMuscleId, true))

        for(muscle in exerciseWithMuscles.secondaryMuscles) {
            val muscleId = muscleRepository.getMuscleId(muscle)
            muscleRepository.addExerciseMuscleCrossRef(ExerciseMuscleCrossRef(exercise.id, muscleId, false))
        }
    }
}