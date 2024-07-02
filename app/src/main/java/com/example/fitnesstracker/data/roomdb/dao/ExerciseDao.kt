package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.data.roomdb.entity.ExerciseEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat

@Dao
interface ExerciseDao {
    @Insert
    fun insert(it: ExerciseEntity)

    @Query("Select * from exercises")
    suspend fun getExercises(): List<ExerciseEntity>

    @Query("Select * from exercises " +
            "where id=:exerciseId")
    suspend fun getExerciseById(exerciseId: Int): ExerciseEntity

    @Insert
    suspend fun addExercise(exercise: ExerciseEntity): Long

    @Query("select m.name " +
            "from exercises e " +
            "join exerciseMuscleCrossRef em on em.exerciseId = e.id " +
            "join muscles m on m.id = em.muscleId " +
            "where e.id = :exerciseId and em.isPrimary = 1")
    suspend fun getExercisePrimaryMuscle(exerciseId: Int): String

    @Query("select m.name " +
            "from exercises e " +
            "join exerciseMuscleCrossRef em on em.exerciseId = e.id " +
            "join muscles m on m.id = em.muscleId " +
            "where e.id = :exerciseId and em.isPrimary = 0")
    suspend fun getExerciseSecondaryMuscles(exerciseId: Int): List<String>

    @Transaction
    suspend fun getExerciseWithMuscles(exerciseId: Int): ExerciseWithMuscles {
        val exercise = getExerciseById(exerciseId)
        val primaryMuscle = getExercisePrimaryMuscle(exerciseId)
        val secondaryMuscles = getExerciseSecondaryMuscles(exerciseId)

        return ExerciseWithMuscles(
            exercise.id,
            exercise.name,
            exercise.description,
            exercise.equipment,
            primaryMuscle,
            secondaryMuscles
        )
    }

    @Update
    fun updateExercise(entity: ExerciseEntity)
}