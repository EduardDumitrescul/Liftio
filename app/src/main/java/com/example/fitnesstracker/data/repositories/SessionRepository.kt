package com.example.fitnesstracker.data.repositories

import androidx.datastore.preferences.core.Preferences
import com.example.fitnesstracker.data.datastore.SessionPreferences
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getSessionPreferences(): Flow<SessionPreferences>

    suspend fun removeOngoingWorkout()
    suspend fun updateOngoingWorkout(id: Int)
    fun getSetsCompleted(): Flow<Int>
    suspend fun updateCompletedSets(value: Int)
    fun getExercisesCompleted(): Flow<Int>
    suspend fun updateCompletedExercises(value: Int)
    suspend fun updateDuration(value: Long)
}