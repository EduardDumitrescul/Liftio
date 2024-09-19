package com.thinkerbyte.fitnesstracker.data.roomdb.repository

import com.thinkerbyte.fitnesstracker.data.dto.DateWithSets
import com.thinkerbyte.fitnesstracker.data.models.ExerciseSet
import com.thinkerbyte.fitnesstracker.data.repositories.SetRepository
import com.thinkerbyte.fitnesstracker.data.roomdb.dao.SetDao
import com.thinkerbyte.fitnesstracker.data.roomdb.entity.toEntity
import com.thinkerbyte.fitnesstracker.data.roomdb.entity.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
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

    override suspend fun updateSetIndexes(workoutExerciseCrossRefId: Int, indexToBeRemoved: Int) {
        setDao.updateIndexes(workoutExerciseCrossRefId, indexToBeRemoved)
    }

    override suspend fun getSet(id: Int): ExerciseSet {
        val entity = setDao.getSet(id)
        return entity.toModel()
    }

    override suspend fun getSetsForWorkoutExercise(workoutExerciseCrossRefId: Int): List<ExerciseSet> {
        val entities = setDao.getSetsByWorkoutExercise(workoutExerciseCrossRefId)
        return entities.map {it.toModel()}
    }

    override suspend fun addSet(set: ExerciseSet) {
        setDao.insertSet(set.toEntity())
    }

    override suspend fun updateSet(set: ExerciseSet) {
        setDao.updateSet(set.toEntity())
    }

    override fun getSetsHistory(exerciseId: Int): Flow<List<DateWithSets>> {
        val flow = setDao.getSetsGroupedByDate(exerciseId)
        return flow.map { map ->
            map.map { mapEntry ->
                DateWithSets(
                    date = mapEntry.key.timeStarted,
                    sets = mapEntry.value.map { it.toModel() }
                )
            }
        }
        return emptyFlow()
    }
}