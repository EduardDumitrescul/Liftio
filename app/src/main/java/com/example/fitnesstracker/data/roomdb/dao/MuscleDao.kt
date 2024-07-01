package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.fitnesstracker.data.roomdb.entity.MuscleEntity

@Dao
interface MuscleDao {
    @Insert
    fun insert(it: MuscleEntity)
}