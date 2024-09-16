package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.datastore.SessionPreferences
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getSessionPreferences(): Flow<SessionPreferences>

    suspend fun removeOngoingWorkout()
    suspend fun addOngoingWorkout(id: Int)
}