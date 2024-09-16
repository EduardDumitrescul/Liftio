package com.example.fitnesstracker.services

import com.example.fitnesstracker.data.datastore.SessionPreferences
import com.example.fitnesstracker.data.repositories.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SessionService @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    fun getSessionPreferences(): Flow<SessionPreferences> {
        return sessionRepository.getSessionPreferences()
    }

    suspend fun completeSet() {
        val completedSets = sessionRepository.getSetsCompleted()
        sessionRepository.updateCompletedSets(completedSets.first() + 1)
    }

    suspend fun completeExercise() {
        val completedExercises = sessionRepository.getExercisesCompleted()
        sessionRepository.updateCompletedExercises(completedExercises.first() + 1)
        sessionRepository.updateCompletedSets(0)
    }

    suspend fun updateDuration(value: Long) {
        sessionRepository.updateDuration(value)
    }

}