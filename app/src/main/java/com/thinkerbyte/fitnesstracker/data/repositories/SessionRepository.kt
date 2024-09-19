package com.thinkerbyte.fitnesstracker.data.repositories

import com.thinkerbyte.fitnesstracker.data.datastore.SessionPreferences
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface SessionRepository {
    fun getSessionPreferences(): Flow<SessionPreferences>

    suspend fun removeOngoingWorkout()
    suspend fun updateOngoingWorkout(id: Int)
    fun getSetsCompleted(): Flow<Int>
    suspend fun updateCompletedSets(value: Int)
    fun getExercisesCompleted(): Flow<Int>
    suspend fun updateCompletedExercises(value: Int)
    suspend fun updateTimeStarted(value: LocalDateTime)
}