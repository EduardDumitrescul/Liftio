package com.example.fitnesstracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.fitnesstracker.data.entity.ExerciseEntity
import com.example.fitnesstracker.data.entity.MuscleEntity

@Dao
interface MuscleDao {
    @Insert
    fun insert(it: MuscleEntity)
}