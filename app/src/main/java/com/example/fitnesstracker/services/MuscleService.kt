package com.example.fitnesstracker.services

import com.example.fitnesstracker.data.repositories.MuscleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MuscleService @Inject constructor(
    private val muscleRepository: MuscleRepository
) {
    suspend fun getMuscleNames(): List<String> {
        return muscleRepository.getMuscleNames()
    }
}