package com.example.fitnesstracker.services

import com.example.fitnesstracker.data.dto.OverviewStatistics
import com.example.fitnesstracker.data.repositories.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class AnalysisService @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    fun getOverviewStatistics(): Flow<OverviewStatistics> {
        val workoutsCompletedFlow = workoutRepository.getNumberOfWorkoutsCompleted()
        val timeTrainedFlow = workoutRepository.getTimeTrained()
        val setsCompletedFlow = workoutRepository.getSetsCompleted()

        return combine(
            workoutsCompletedFlow,
            timeTrainedFlow,
            setsCompletedFlow
        ) { workoutsCompleted, timeTrained, setsCompleted ->
            OverviewStatistics(
                workoutsCompleted = workoutsCompleted,
                timeTrained = timeTrained,
                setsCompleted = setsCompleted
            )
        }
    }

    fun getWorkoutDates(): Flow<List<LocalDateTime>> {
        return workoutRepository.getWorkoutDates()
    }
}