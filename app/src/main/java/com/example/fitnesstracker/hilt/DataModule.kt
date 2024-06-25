package com.example.fitnesstracker.hilt

import com.example.fitnesstracker.service.ExerciseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//package com.example.fitnesstracker.data
//
//import android.content.Context
//import com.example.fitnesstracker.data.daos.ExerciseDao
//import com.example.fitnesstracker.data.daos.ExerciseMuscleDao
//import com.example.fitnesstracker.data.daos.MuscleDao
//import com.example.fitnesstracker.data.daos.SetDao
//import com.example.fitnesstracker.data.daos.TemplateDao
//import com.example.fitnesstracker.data.daos.TemplateExerciseDao
//import com.example.fitnesstracker.data.daos.TestDao
//import com.example.fitnesstracker.data.daos.WorkoutDao
//import com.example.fitnesstracker.data.repositories.ExerciseRepository
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//private const val TAG = "DATA MODULE"
//
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideExerciseService(): ExerciseService {
        return ExerciseService()
    }

//    @Provides
//    @Singleton
//    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
//        return AppDatabase.getInstance(appContext)
//    }
//
//    @Provides
//    fun provideChannelDao(appDatabase: AppDatabase): TestDao {
//        return appDatabase.testDao()
//    }
//
//    @Provides
//    fun provideExerciseMuscleDao(appDatabase: AppDatabase): ExerciseMuscleDao {
//        return appDatabase.exerciseMuscleDao()
//    }
//
//    @Provides
//    fun provideExerciseDao(appDatabase: AppDatabase): ExerciseDao {
//        return appDatabase.exerciseDao()
//    }
//
//    @Provides
//    fun provideExerciseRepository(
//        exerciseDao: ExerciseDao
//    ): ExerciseRepository {
//        return ExerciseRepository(exerciseDao)
//    }
//
//    @Provides
//    fun provideMuscleDao(appDatabase: AppDatabase): MuscleDao {
//        return appDatabase.muscleDao()
//    }
//
//    @Provides
//    fun provideMuscleRepository(
//        muscleDao: MuscleDao
//    ): MuscleRepository {
//        return MuscleRepository(muscleDao)
//    }
//
//    @Provides
//    fun provideSetDao(appDatabase: AppDatabase): SetDao {
//        return appDatabase.setDao()
//    }
//
//    @Provides
//    fun provideSetRepository(
//        setDao: SetDao
//    ): SetRepository {
//        return SetRepository(setDao)
//    }
//
//    @Provides
//    fun provideTemplateExerciseDao(appDatabase: AppDatabase): TemplateExerciseDao {
//        return appDatabase.templateExerciseDao()
//    }
//
//    @Provides
//    fun provideTemplateExerciseRepository(
//        templateExerciseDao: TemplateExerciseDao
//    ): TemplateExerciseRepository {
//        return TemplateExerciseRepository(templateExerciseDao)
//    }
//
//    @Provides
//    fun provideTemplateDao(appDatabase: AppDatabase): TemplateDao {
//        return appDatabase.templateDao()
//    }
//
//    @Provides
//    fun provideTemplateRepository(
//        templateDao: TemplateDao
//    ): TemplateRepository {
//        return TemplateRepository(templateDao)
//    }
//
//    @Provides
//    fun provideWorkoutDao(appDatabase: AppDatabase): WorkoutDao {
//        return appDatabase.workoutDao()
//    }
//
//    @Provides
//    fun provideWorkoutRepository(
//        workoutDao: WorkoutDao
//    ): WorkoutRepository {
//        return WorkoutRepository(workoutDao)
//    }
}