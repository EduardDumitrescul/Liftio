package com.thinkerbyte.fitnesstracker.hilt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.thinkerbyte.fitnesstracker.data.datastore.DataStoreSessionRepository
import com.thinkerbyte.fitnesstracker.data.datastore.DataStoreSettingsRepository
import com.thinkerbyte.fitnesstracker.data.repositories.ExerciseRepository
import com.thinkerbyte.fitnesstracker.data.repositories.MuscleRepository
import com.thinkerbyte.fitnesstracker.data.repositories.SessionRepository
import com.thinkerbyte.fitnesstracker.data.repositories.SetRepository
import com.thinkerbyte.fitnesstracker.data.repositories.SettingsRepository
import com.thinkerbyte.fitnesstracker.data.repositories.WorkoutRepository
import com.thinkerbyte.fitnesstracker.data.roomdb.AppDatabase
import com.thinkerbyte.fitnesstracker.data.roomdb.dao.ExerciseDao
import com.thinkerbyte.fitnesstracker.data.roomdb.dao.MuscleDao
import com.thinkerbyte.fitnesstracker.data.roomdb.dao.SetDao
import com.thinkerbyte.fitnesstracker.data.roomdb.dao.WorkoutDao
import com.thinkerbyte.fitnesstracker.data.roomdb.repository.RoomExerciseRepository
import com.thinkerbyte.fitnesstracker.data.roomdb.repository.RoomMuscleRepository
import com.thinkerbyte.fitnesstracker.data.roomdb.repository.RoomSetRepository
import com.thinkerbyte.fitnesstracker.data.roomdb.repository.RoomWorkoutRepository
import com.thinkerbyte.fitnesstracker.services.ExerciseService
import com.thinkerbyte.fitnesstracker.services.SetService
import com.thinkerbyte.fitnesstracker.services.WorkoutService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val SETTINGS_NAME = "settings"
private val Context.dataStore by preferencesDataStore(
    name = SETTINGS_NAME
)

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return appContext.dataStore
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(dataStore: DataStore<Preferences>): SettingsRepository {
        return DataStoreSettingsRepository(dataStore)
    }

    @Provides
    @Singleton
    fun provideSessionRepository(dataStore: DataStore<Preferences>): SessionRepository {
        return DataStoreSessionRepository(dataStore)
    }

    @Provides
    @Singleton
    fun provideExerciseService(
        exerciseRepository: ExerciseRepository,
        muscleRepository: MuscleRepository)
    : ExerciseService {
        return ExerciseService(exerciseRepository, muscleRepository)
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(
        exerciseDao: ExerciseDao,
        muscleDao: MuscleDao,
        setDao: SetDao
    ): ExerciseRepository {
        return RoomExerciseRepository(exerciseDao, muscleDao, setDao)
    }

    @Provides
    @Singleton
    fun provideMuscleRepository(
        muscleDao: MuscleDao
    ): MuscleRepository {
        return RoomMuscleRepository(muscleDao)
    }

    @Provides
    @Singleton
    fun provideMuscleDao(
        appDatabase: AppDatabase
    ): MuscleDao {
        return appDatabase.muscleDao()
    }

    @Provides
    @Singleton
    fun provideExerciseDao(
        appDatabase: AppDatabase
    ): ExerciseDao {
        return appDatabase.exerciseDao()
    }

    @Provides
    @Singleton
    fun provideWorkoutService(
        workoutRepository: WorkoutRepository,
        exerciseRepository: ExerciseRepository,
        muscleRepository: MuscleRepository,
        setRepository: SetRepository,
        sessionRepository: SessionRepository
    ): WorkoutService {
        return WorkoutService(
            workoutRepository,
            setRepository,
            muscleRepository,
            exerciseRepository,
            sessionRepository
        )
    }

    @Provides
    @Singleton
    fun provideWorkoutRepository(
        exerciseDao: ExerciseDao,
        muscleDao: MuscleDao,
        setDao: SetDao,
        workoutDao: WorkoutDao
    ): WorkoutRepository {
        return RoomWorkoutRepository(
            exerciseDao,
            muscleDao,
            setDao,
            workoutDao
        )
    }

    @Provides
    @Singleton
    fun provideWorkoutDao(
        appDatabase: AppDatabase
    ): WorkoutDao {
        return appDatabase.workoutDao()
    }

    @Provides
    @Singleton
    fun provideSetDao(
        appDatabase: AppDatabase
    ): SetDao {
        return appDatabase.setDao()
    }

    @Provides
    @Singleton
    fun provideSetRepository(
        setDao: SetDao
    ):SetRepository {
        return RoomSetRepository(setDao)
    }

    @Provides
    @Singleton
    fun provideSetService(setRepository: SetRepository): SetService {
        return SetService(setRepository)
    }


}