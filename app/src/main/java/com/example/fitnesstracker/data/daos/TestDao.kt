package com.example.fitnesstracker.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.fitnesstracker.data.entities.TestEntity

@Dao
interface TestDao
{
    @Query("SELECT * FROM tests")
    fun getAll(): List<TestEntity>
}