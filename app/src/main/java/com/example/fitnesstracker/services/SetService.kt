package com.example.fitnesstracker.services

import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.data.repositories.SetRepository
import com.example.fitnesstracker.data.roomdb.dao.SetDao
import com.example.fitnesstracker.data.roomdb.entity.toEntity
import javax.inject.Inject

class SetService @Inject constructor(
    private val setRepository: SetRepository
) {
    suspend fun updateSet(set: ExerciseSet) {
        setRepository.insertSet(set)
    }
}