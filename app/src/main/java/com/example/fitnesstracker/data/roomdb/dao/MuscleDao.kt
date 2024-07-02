package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnesstracker.data.models.ExerciseMuscleCrossRef
import com.example.fitnesstracker.data.roomdb.entity.ExerciseMuscleCrossRefEntity
import com.example.fitnesstracker.data.roomdb.entity.MuscleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MuscleDao {
    @Insert
    fun insert(it: MuscleEntity)

    @Query("Select name from muscles")
    suspend fun getMuscleNames(): List<String>

    @Query("Select * from muscles where id = :id")
    suspend fun getMuscleById(id: Int): MuscleEntity?

    @Query("Select m.id, m.name from exerciseMuscleCrossRef e " +
            "join muscles m on e.muscleId = m.id " +
            "where e.exerciseId = :exerciseId " +
            "and e.isPrimary = 1"
    )
    suspend fun getPrimaryMuscleByExerciseId(exerciseId: Int): MuscleEntity?

    @Query("Select m.id, m.name from exerciseMuscleCrossRef e " +
            "join muscles m on e.muscleId = m.id " +
            "where e.exerciseId = :exerciseId " +
            "and e.isPrimary = 0"
    )
    suspend fun getSecondaryMusclesByExerciseId(exerciseId: Int): List<MuscleEntity>

    @Query("select id from muscles where name like :name")
    fun getMuscleIdByName(name: String): Int

    @Insert
    fun insert(it: ExerciseMuscleCrossRefEntity)


    @Query("delete from exerciseMuscleCrossRef where exerciseId = :exerciseId")
    fun removeExerciseMuscleRefs(exerciseId: Int)

}