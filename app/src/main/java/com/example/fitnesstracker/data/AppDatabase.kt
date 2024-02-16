package com.example.fitnesstracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitnesstracker.data.daos.ExerciseDao
import com.example.fitnesstracker.data.daos.ExerciseMuscleDao
import com.example.fitnesstracker.data.daos.MuscleDao
import com.example.fitnesstracker.data.daos.SetDao
import com.example.fitnesstracker.data.daos.TemplateExerciseDao
import com.example.fitnesstracker.data.daos.TestDao
import com.example.fitnesstracker.data.entities.ExerciseEntity
import com.example.fitnesstracker.data.entities.ExerciseMuscleEntity
import com.example.fitnesstracker.data.entities.MuscleEntity
import com.example.fitnesstracker.data.entities.SetEntity
import com.example.fitnesstracker.data.entities.TemplateExerciseEntity
import com.example.fitnesstracker.data.entities.TestEntity

@Database(
    entities = [
        TestEntity::class,
        ExerciseMuscleEntity::class,
        ExerciseEntity::class,
        MuscleEntity::class,
        SetEntity::class,
        TemplateExerciseEntity::class,
               ],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao

    abstract fun exerciseMuscleDao(): ExerciseMuscleDao

    abstract fun exerciseDao(): ExerciseDao

    abstract fun muscleDao(): MuscleDao

    abstract fun setDao(): SetDao

    abstract fun templateExerciseDao(): TemplateExerciseDao
}