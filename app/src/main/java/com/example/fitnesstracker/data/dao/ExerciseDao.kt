package com.example.fitnesstracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.fitnesstracker.data.entity.ExerciseEntity

@Dao
interface ExerciseDao {
    @Insert
    fun insert(it: ExerciseEntity)
}