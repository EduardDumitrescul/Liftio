package com.example.fitnesstracker.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.fitnesstracker.data.entities.ExerciseMuscleEntity

@Dao
interface ExerciseMuscleDao: IDao<ExerciseMuscleEntity> {
    @Query("select * from ExerciseMuscle")
    fun getAll(): LiveData<ExerciseMuscleEntity>


}