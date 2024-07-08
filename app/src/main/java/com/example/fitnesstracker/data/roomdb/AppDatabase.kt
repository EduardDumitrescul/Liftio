package com.example.fitnesstracker.data.roomdb

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fitnesstracker.data.roomdb.converters.LocalDateTypeConverter
import com.example.fitnesstracker.data.roomdb.dao.ExerciseDao
import com.example.fitnesstracker.data.roomdb.dao.MuscleDao
import com.example.fitnesstracker.data.roomdb.entity.ExerciseEntity
import com.example.fitnesstracker.data.roomdb.entity.ExerciseMuscleCrossRefEntity
import com.example.fitnesstracker.data.roomdb.entity.MuscleEntity
import kotlinx.coroutines.Dispatchers

private const val TAG = "AppDatabase"

@Database(
    entities = [
        ExerciseEntity::class,
        MuscleEntity::class,
        ExerciseMuscleCrossRefEntity::class
               ],
    version = 2)
@TypeConverters(LocalDateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
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
//                .fallbackToDestructiveMigrationOnDowngrade()
                .addCallback(seedDatabaseCallback(context))
                .build()

        private fun seedDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                private var called = false
                override fun onCreate(db: SupportSQLiteDatabase) {
                    Log.d(TAG, "seedDatabaseCallback()")
                    super.onCreate(db)
                    called = true
                    ioThread {
                        val seeder = Seeder(context, getInstance(context))
                        seeder.seed()
                    }
                }
                override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                    super.onDestructiveMigration(db)
                    if(!called) {
                        ioThread {
                            val seeder = Seeder(context, getInstance(context))
                            seeder.seed()
                        }
                    }

                }
            }
        }
    }
}