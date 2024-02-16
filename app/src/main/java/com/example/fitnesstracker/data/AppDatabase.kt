package com.example.fitnesstracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitnesstracker.data.converters.LocalDateTypeConverter
import com.example.fitnesstracker.data.daos.ExerciseDao
import com.example.fitnesstracker.data.daos.ExerciseMuscleDao
import com.example.fitnesstracker.data.daos.MuscleDao
import com.example.fitnesstracker.data.daos.SetDao
import com.example.fitnesstracker.data.daos.TemplateDao
import com.example.fitnesstracker.data.daos.TemplateExerciseDao
import com.example.fitnesstracker.data.daos.TestDao
import com.example.fitnesstracker.data.daos.WorkoutDao
import com.example.fitnesstracker.data.entities.ExerciseEntity
import com.example.fitnesstracker.data.entities.ExerciseMuscleEntity
import com.example.fitnesstracker.data.entities.MuscleEntity
import com.example.fitnesstracker.data.entities.SetEntity
import com.example.fitnesstracker.data.entities.TemplateEntity
import com.example.fitnesstracker.data.entities.TemplateExerciseEntity
import com.example.fitnesstracker.data.entities.TestEntity
import com.example.fitnesstracker.data.entities.WorkoutEntity

@Database(
    entities = [
        TestEntity::class,
        ExerciseMuscleEntity::class,
        ExerciseEntity::class,
        MuscleEntity::class,
        SetEntity::class,
        TemplateExerciseEntity::class,
        TemplateEntity::class,
        WorkoutEntity::class,
               ],
    version = 1)
@TypeConverters(LocalDateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao

    abstract fun exerciseMuscleDao(): ExerciseMuscleDao

    abstract fun exerciseDao(): ExerciseDao

    abstract fun muscleDao(): MuscleDao

    abstract fun setDao(): SetDao

    abstract fun templateExerciseDao(): TemplateExerciseDao

    abstract fun templateDao(): TemplateDao

    abstract fun workoutDao(): WorkoutDao
}