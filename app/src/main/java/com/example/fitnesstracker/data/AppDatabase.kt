package com.example.fitnesstracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitnesstracker.data.daos.TestDao
import com.example.fitnesstracker.data.entities.TestEntity

@Database(entities = [TestEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao
}