package com.example.fitnesstracker.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.fitnesstracker.data.entities.TemplateEntity

@Dao
interface TemplateDao: IDao<TemplateEntity> {
    @Query("select * from templates")
    fun getAll(): LiveData<List<TemplateEntity>>
}