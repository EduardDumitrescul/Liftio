package com.example.fitnesstracker.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fitnesstracker.data.converters.LocalDateTypeConverter
import com.example.fitnesstracker.data.dao.ExerciseDao
import com.example.fitnesstracker.data.dao.ExerciseMuscleDao
import com.example.fitnesstracker.data.dao.MuscleDao
import com.example.fitnesstracker.data.entity.ExerciseEntity
import com.example.fitnesstracker.data.entity.ExerciseMuscleCrossRefEntity
import com.example.fitnesstracker.data.entity.MuscleEntity

private const val TAG = "APP DATABASE"

@Database(
    entities = [
        ExerciseEntity::class,
        MuscleEntity::class,
        ExerciseMuscleCrossRefEntity::class
               ],
    version = 1)
@TypeConverters(LocalDateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseMuscleDao(): ExerciseMuscleDao

    abstract fun exerciseDao(): ExerciseDao

    abstract fun muscleDao(): MuscleDao
//
//    abstract fun setDao(): SetDao
//
//    abstract fun templateExerciseDao(): TemplateExerciseDao
//
//    abstract fun templateDao(): TemplateDao
//
//    abstract fun workoutDao(): WorkoutDao

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
//                    ioThread {
                        val seeder = Seeder(context, getInstance(context))
                        seeder.seed()
//                    }
                }
            }
        }
    }
}