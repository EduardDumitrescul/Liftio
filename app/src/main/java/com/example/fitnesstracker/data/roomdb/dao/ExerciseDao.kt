package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.fitnesstracker.data.roomdb.entity.ExerciseEntity

@Dao
interface ExerciseDao {
    @Insert
    fun insert(it: ExerciseEntity)
}