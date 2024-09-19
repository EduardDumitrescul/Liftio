package com.thinkerbyte.fitnesstracker.services

import com.thinkerbyte.fitnesstracker.data.models.ExerciseSet
import com.thinkerbyte.fitnesstracker.data.repositories.SetRepository
import javax.inject.Inject

class SetService @Inject constructor(
    private val setRepository: SetRepository
) {
    suspend fun updateSet(set: ExerciseSet) {
        setRepository.insertSet(set)
    }
}