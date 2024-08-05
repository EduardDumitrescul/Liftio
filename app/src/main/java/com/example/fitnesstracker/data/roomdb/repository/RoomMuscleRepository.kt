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
    override fun getMuscleNames(): Flow<List<String>> {
        return muscleDao.getMuscleNames()
    }

    override fun getMuscleById(id: Int): Flow<Muscle?> {
        val entity = muscleDao.getMuscleById(id)
        return entity.map { it?.toModel() }
    }

    override fun getPrimaryMuscleByExerciseId(id: Int): Flow<Muscle?> {
        val entity = muscleDao.getPrimaryMuscleByExerciseId(id)
        return entity.map { it?.toModel() }
    }

    override fun getSecondaryMusclesByExerciseId(id: Int): Flow<List<Muscle>> {
        val entities = muscleDao.getSecondaryMusclesByExerciseId(id)
        return entities.map { muscles ->
                muscles.map {
                    it.toModel()
                }
            }
        }

    override suspend fun getMuscleId(name: String): Int {
        return muscleDao.getMuscleIdByName(name)
    }

    override suspend fun addExerciseMuscleCrossRef(exerciseMuscleCrossRef: ExerciseMuscleCrossRef) {
        val entity=  exerciseMuscleCrossRef.toEntity()
        muscleDao.insert(entity)
    }

    override suspend fun removeExerciseMuscleRefs(exerciseId: Int) {
        muscleDao.removeExerciseMuscleRefs(exerciseId)
    }

    override fun getMusclesByWorkoutId(id: Int): Flow<List<Muscle>> {
        val entities = muscleDao.getMusclesByWorkoutId(id)
        return entities.map { list ->
            list.map {
                it.toModel()
            }
        }
    }

}