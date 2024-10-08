package com.thinkerbyte.fitnesstracker.services

import com.thinkerbyte.fitnesstracker.data.dto.ExerciseWithMuscles
import com.thinkerbyte.fitnesstracker.data.models.Exercise
import com.thinkerbyte.fitnesstracker.data.models.ExerciseMuscleCrossRef
import com.thinkerbyte.fitnesstracker.data.repositories.ExerciseRepository
import com.thinkerbyte.fitnesstracker.data.repositories.MuscleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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

        val primaryMuscleId = muscleRepository.getMuscleId(exerciseWithMuscles.primaryMuscle.name)
        muscleRepository.addExerciseMuscleCrossRef(ExerciseMuscleCrossRef(exerciseId, primaryMuscleId, true))

        for(muscle in exerciseWithMuscles.secondaryMuscles) {
            val muscleId = muscleRepository.getMuscleId(muscle.name)
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

        val primaryMuscleId = muscleRepository.getMuscleId(exerciseWithMuscles.primaryMuscle.name)
        muscleRepository.addExerciseMuscleCrossRef(ExerciseMuscleCrossRef(exercise.id, primaryMuscleId, true))

        for(muscle in exerciseWithMuscles.secondaryMuscles) {
            val muscleId = muscleRepository.getMuscleId(muscle.name)
            muscleRepository.addExerciseMuscleCrossRef(ExerciseMuscleCrossRef(exercise.id, muscleId, false))
        }
    }

    fun getExercise(id: Int): Flow<Exercise> {
        return exerciseRepository.getExerciseById(id)
    }
}