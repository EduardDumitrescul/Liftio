package com.example.fitnesstracker.data.roomdb.repository

import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.data.repositories.SetRepository
import com.example.fitnesstracker.data.roomdb.dao.SetDao
import com.example.fitnesstracker.data.roomdb.entity.toEntity
import javax.inject.Inject

class RoomSetRepository @Inject constructor(
    private val setDao: SetDao
): SetRepository {
    override suspend fun insertSet(set: ExerciseSet) {
        setDao.insertSet(set.toEntity())
    }
}