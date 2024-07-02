package com.example.fitnesstracker.data.roomdb.repository

import com.example.fitnesstracker.data.models.ExerciseMuscleCrossRef
import com.example.fitnesstracker.data.models.Muscle
import com.example.fitnesstracker.data.repositories.MuscleRepository
import com.example.fitnesstracker.data.roomdb.dao.MuscleDao
import com.example.fitnesstracker.data.roomdb.entity.toEntity
import com.example.fitnesstracker.data.roomdb.entity.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomMuscleRepository @Inject constructor(
    private val muscleDao: MuscleDao
): MuscleRepository {
    override suspend fun getMuscleNames(): List<String> {
        return muscleDao.getMuscleNames()
    }

    override suspend fun getMuscleById(id: Int): Muscle? {
        val entity = muscleDao.getMuscleById(id)
        return entity?.toModel()
    }

    override suspend fun getPrimaryMuscleByExerciseId(id: Int): Muscle? {
        val entity = muscleDao.getPrimaryMuscleByExerciseId(id)
        return entity?.toModel()
    }

    override suspend fun getSecondaryMusclesByExerciseId(id: Int): List<Muscle> {
        val entities = muscleDao.getSecondaryMusclesByExerciseId(id)
        return entities.map {
                it.toModel()
            }
        }

    override suspend fun getMuscleId(name: String): Int {
        return muscleDao.getMuscleIdByName(name)
    }

    override suspend fun addExerciseMuscleCrossRef(exerciseMuscleCrossRef: ExerciseMuscleCrossRef) {
        val entity=  exerciseMuscleCrossRef.toEntity()
        muscleDao.insert(entity)
    }

}