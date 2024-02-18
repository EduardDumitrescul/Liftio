package com.example.fitnesstracker.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
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
import com.example.fitnesstracker.ioThread
import java.util.UUID

private const val TAG = "APP DATABASE"

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

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "FitnessDb")
                .fallbackToDestructiveMigration()
                .addCallback(seedDatabaseCallback(context))
                .build()

        private fun seedDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    Log.d(TAG, "seedDatabaseCallback()")
                    super.onCreate(db)
                    ioThread {
                        val testDao = getInstance(context).testDao()
                        testDao.insert(TestEntity(UUID.randomUUID()))
                    }
                }
            }
        }
    }
}