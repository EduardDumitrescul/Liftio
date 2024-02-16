package com.example.fitnesstracker.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.fitnesstracker.data.entities.TemplateExerciseEntity

@Dao
interface TemplateExerciseDao: IDao<TemplateExerciseEntity> {
    @Query("select * from templateExercises")
    fun getAll(): LiveData<List<TemplateExerciseEntity>>
}