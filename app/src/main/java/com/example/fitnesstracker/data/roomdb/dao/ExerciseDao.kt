package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnesstracker.data.roomdb.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    fun insert(it: ExerciseEntity)

    @Query("Select * from exercises")
    fun getExercises(): Flow<List<ExerciseEntity>>

    @Insert
    suspend fun addExercise(exercise: ExerciseEntity): Long
}