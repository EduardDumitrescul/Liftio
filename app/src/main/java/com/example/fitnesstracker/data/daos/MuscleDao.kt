package com.example.fitnesstracker.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.fitnesstracker.data.entities.MuscleEntity

@Dao
interface MuscleDao: IDao<MuscleEntity> {
    @Query("select * from muscles")
    fun getAll(): LiveData<MuscleEntity>

}