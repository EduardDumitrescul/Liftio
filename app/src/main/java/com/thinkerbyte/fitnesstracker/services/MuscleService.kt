package com.thinkerbyte.fitnesstracker.services

import com.thinkerbyte.fitnesstracker.data.models.Muscle
import com.thinkerbyte.fitnesstracker.data.repositories.MuscleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MuscleService @Inject constructor(
    private val muscleRepository: MuscleRepository
) {
    fun getMuscleNames(): Flow<List<String>> {
        return muscleRepository.getMuscleNames()
    }

    fun getMuscles(): Flow<List<Muscle>> {
        return muscleRepository.getMuscles()
    }
}