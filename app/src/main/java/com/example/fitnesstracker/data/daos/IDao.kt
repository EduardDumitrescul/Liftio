package com.example.fitnesstracker.data.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface IDao<Entity> {

    @Insert
    fun insert(entity: Entity)

    @Update
    fun update(entity: Entity)

    @Delete
    fun delete(entity: Entity)
}