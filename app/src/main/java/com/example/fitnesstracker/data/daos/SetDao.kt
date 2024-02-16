package com.example.fitnesstracker.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.fitnesstracker.data.entities.SetEntity

@Dao
interface SetDao: IDao<SetEntity> {
    @Query("select * from sets")
    fun getALl(): LiveData<List<SetEntity>>
}