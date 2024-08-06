package com.example.fitnesstracker.services

import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.data.repositories.SetRepository
import javax.inject.Inject

class SetService @Inject constructor(
    private val setRepository: SetRepository
) {
    suspend fun updateSet(set: ExerciseSet) {
        setRepository.insertSet(set)
    }
}