package com.example.fitnesstracker.data.services

import com.example.fitnesstracker.data.repositories.MuscleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MuscleService @Inject constructor(
    private val muscleRepository: MuscleRepository
) {
    fun getMuscleNames(): Flow<List<String>> {
        return muscleRepository.getMuscleNames()
    }
}