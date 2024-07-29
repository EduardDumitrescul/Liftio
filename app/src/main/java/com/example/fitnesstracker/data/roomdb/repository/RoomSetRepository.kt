package com.example.fitnesstracker.data.roomdb.repository

import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.data.repositories.SetRepository
import com.example.fitnesstracker.data.roomdb.dao.SetDao
import com.example.fitnesstracker.data.roomdb.entity.toEntity
import com.example.fitnesstracker.data.roomdb.entity.toModel
import javax.inject.Inject

class RoomSetRepository @Inject constructor(
    private val setDao: SetDao
): SetRepository {
    override suspend fun insertSet(set: ExerciseSet) {
        setDao.insertSet(set.toEntity())
    }

    override suspend fun removeSet(id: Int) {
        setDao.removeSet(id)
    }

    override suspend fun updateSetIndexes(templateExerciseCrossRefId: Int, indexToBeRemoved: Int) {
        setDao.updateIndexes(templateExerciseCrossRefId, indexToBeRemoved)
    }

    override suspend fun getSet(id: Int): ExerciseSet {
        val entity = setDao.getSet(id)
        return entity.toModel()
    }

    override suspend fun getSetsForTemplateExercise(templateExerciseCrossRefId: Int): List<ExerciseSet> {
        val entities = setDao.getSetsByTemplateExercise(templateExerciseCrossRefId)
        return entities.map {it.toModel()}
    }

    override suspend fun addSet(set: ExerciseSet) {
        setDao.insertSet(set.toEntity())
    }

    override suspend fun updateSet(set: ExerciseSet) {
        setDao.updateSet(set.toEntity())
    }
}