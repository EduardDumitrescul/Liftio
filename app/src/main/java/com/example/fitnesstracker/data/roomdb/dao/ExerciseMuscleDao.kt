package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.fitnesstracker.data.roomdb.entity.ExerciseMuscleCrossRefEntity

@Dao
interface ExerciseMuscleDao {
    @Insert
    fun insert(it: ExerciseMuscleCrossRefEntity)
}