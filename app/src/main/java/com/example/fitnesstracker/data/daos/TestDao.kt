package com.example.fitnesstracker.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.fitnesstracker.data.entities.TestEntity

@Dao
interface TestDao: IDao<TestEntity>
{
    @Query("SELECT * FROM tests")
    fun getAll(): LiveData<List<TestEntity>>
}