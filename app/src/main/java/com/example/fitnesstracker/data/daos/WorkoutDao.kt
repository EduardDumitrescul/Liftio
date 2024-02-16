package com.example.fitnesstracker.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.fitnesstracker.data.entities.WorkoutEntity

@Dao
interface WorkoutDao: IDao<WorkoutEntity> {
    @Query("select * from workouts")
    fun getAll(): LiveData<List<WorkoutEntity>>
}