package com.example.fitnesstracker.services

import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.data.models.ExerciseMuscleCrossRef
import com.example.fitnesstracker.data.repositories.ExerciseRepository
import com.example.fitnesstracker.data.repositories.MuscleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "ExerciseService"

class ExerciseService @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val muscleRepository: MuscleRepository
) {

    fun getExercisesWithMuscles(): Flow<List<ExerciseWithMuscles>> {
       return exerciseRepository.getExercisesWithMuscles()
    }

    suspend fun add(exerciseWithMuscles: ExerciseWithMuscles) {
        val exercise = exerciseWithMuscles.exercise
        val exerciseId = exerciseRepository.add(exercise)

        val primaryMuscleId = muscleRepository.getMuscleId(exerciseWithMuscles.primaryMuscle)
        muscleRepository.addExerciseMuscleCrossRef(ExerciseMuscleCrossRef(exerciseId, primaryMuscleId, true))

        for(muscle in exerciseWithMuscles.secondaryMuscles) {
            val muscleId = muscleRepository.getMuscleId(muscle)
            muscleRepository.addExerciseMuscleCrossRef(ExerciseMuscleCrossRef(exerciseId, muscleId, false))
        }
    }

    fun getExerciseWithMuscles(exerciseId: Int): Flow<ExerciseWithMuscles> {
        return exerciseRepository.getExerciseWithMuscles(exerciseId)
    }

    suspend fun updateExercise(exerciseWithMuscles: ExerciseWithMuscles) {
        val exercise = exerciseWithMuscles.exercise
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