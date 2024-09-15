package com.example.fitnesstracker.services

import com.example.fitnesstracker.data.dto.ExerciseWithSets
import com.example.fitnesstracker.data.dto.DateWithSets
import com.example.fitnesstracker.data.dto.WorkoutEntry
import com.example.fitnesstracker.data.models.Workout
import com.example.fitnesstracker.data.repositories.ExerciseRepository
import com.example.fitnesstracker.data.repositories.SetRepository
import com.example.fitnesstracker.data.repositories.WorkoutRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class HistoryService @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    private val setRepository: SetRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getWorkoutEntries(): Flow<List<WorkoutEntry>> {
        val workoutsFlow: Flow<List<Workout>> = workoutRepository.getAllWorkoutEntries()

        val workoutEntries: Flow<List<WorkoutEntry>> = workoutsFlow.flatMapLatest { workouts ->

            val combined = workouts.map { workout ->

                val exercisesWithSetsFlow: Flow<List<ExerciseWithSets>> = exerciseRepository.getExercisesWithSetsByWorkoutId(workout.id)

                combine(exercisesWithSetsFlow) { exercisesWithSets ->
                    WorkoutEntry(
                        id = workout.id,
                        name = workout.name,
                        timeStarted = workout.timeStarted,
                        duration = workout.duration,
                        exercisesWithSets = exercisesWithSets.flatMap { it }
                    )
                }
            }

            combine(combined) {it.toList()}
        }

        return workoutEntries
    }

    fun getSetsWithDate(exerciseId: Int): Flow<List<DateWithSets>> {
        return setRepository.getSetsHistory(exerciseId)
    }

    suspend fun clearHistory() {
        workoutRepository.deleteAllWorkoutEntries()
    }
}