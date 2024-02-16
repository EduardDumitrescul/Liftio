package com.example.fitnesstracker.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.fitnesstracker.data.entities.ExerciseEntity

@Dao
interface ExerciseDao: IDao<ExerciseEntity> {
    @Query("select * from ExerciseEntity")
    fun getAll(): LiveData<ExerciseEntity>
}