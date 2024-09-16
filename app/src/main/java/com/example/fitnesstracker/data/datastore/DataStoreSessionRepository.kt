package com.example.fitnesstracker.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.example.fitnesstracker.data.repositories.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "DataStoreSessionRepository"

class DataStoreSessionRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SessionRepository {
    private object Keys {
        val exists = booleanPreferencesKey("existsOngoingWorkout")
        val workoutId = intPreferencesKey("ongoingWorkoutId")
        val exercisesCompleted = intPreferencesKey("exercisesCompleted")
        val setsCompleted = intPreferencesKey("setsCompleted")
        val duration = longPreferencesKey("duration")
    }

    override fun getSessionPreferences(): Flow<SessionPreferences> {
        return dataStore.data.map {
            mapToSessionPreferences(it)
        }
    }

    override suspend fun removeOngoingWorkout() {

        Log.d(TAG, "remove()")
        dataStore.edit { preferences ->
            preferences[Keys.exists] = false
            preferences[Keys.workoutId] = 0
            preferences[Keys.exercisesCompleted] = 0
            preferences[Keys.setsCompleted] = 0
            preferences[Keys.duration] = 0
        }
    }

    override suspend fun updateOngoingWorkout(id: Int) {
        dataStore.edit { preferences ->
            preferences[Keys.exists] = true
            preferences[Keys.workoutId] = id
        }
    }

    override fun getSetsCompleted(): Flow<Int> {
        return dataStore.data.map {it[Keys.setsCompleted] ?: 0}
    }

    override suspend fun updateCompletedSets(value: Int) {
        dataStore.edit { preferences ->
            preferences[Keys.setsCompleted] = value
        }
    }

    override fun getExercisesCompleted(): Flow<Int> {
        return dataStore.data.map {it[Keys.exercisesCompleted] ?: 0}
    }

    override suspend fun updateCompletedExercises(value: Int) {
        dataStore.edit { preferences ->
            preferences[Keys.exercisesCompleted] = value
        }
    }

    override suspend fun updateDuration(value: Long) {
        dataStore.edit { preferences ->
            preferences[Keys.duration] = value
        }
    }

    private fun mapToSessionPreferences(preferences: Preferences): SessionPreferences {
        val exists: Boolean = preferences[Keys.exists] ?: false
        val workoutId: Int = preferences[Keys.workoutId] ?: 0
        val exercisesCompleted = preferences[Keys.exercisesCompleted] ?: 0
        val setsCompleted = preferences[Keys.setsCompleted] ?: 0
        val duration = preferences[Keys.duration] ?: 0

        return SessionPreferences(
            exists = exists,
            workoutId = workoutId,
            exercisesCompleted = exercisesCompleted,
            setsCompleted = setsCompleted,
            duration = duration
        )
    }

}