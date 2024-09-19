package com.thinkerbyte.fitnesstracker.services

import com.thinkerbyte.fitnesstracker.data.dto.OverviewStatistics
import com.thinkerbyte.fitnesstracker.data.repositories.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDateTime
import javax.inject.Inject

class AnalysisService @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    fun getOverviewStatistics(from: LocalDateTime = LocalDateTime.MIN, to: LocalDateTime = LocalDateTime.MAX): Flow<OverviewStatistics> {
        val workoutsCompletedFlow = workoutRepository.getNumberOfWorkoutsCompleted(from, to)
        val timeTrainedFlow = workoutRepository.getTimeTrained(from, to)
        val setsCompletedFlow = workoutRepository.getSetsCompleted(from, to)

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

    fun getWorkoutDates(from: LocalDateTime = LocalDateTime.MIN, to: LocalDateTime = LocalDateTime.MAX): Flow<List<LocalDateTime>> {
        return workoutRepository.getWorkoutDates(from, to)
    }
}