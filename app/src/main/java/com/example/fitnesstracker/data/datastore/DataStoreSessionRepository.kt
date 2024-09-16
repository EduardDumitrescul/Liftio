package com.example.fitnesstracker.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.fitnesstracker.data.repositories.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "DataStoreSessionRepository"

class DataStoreSessionRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SessionRepository {
    private object Keys {
        val existsOngoingWorkout = booleanPreferencesKey("existsOngoingWorkout")
        val ongoingWorkoutId = intPreferencesKey("ongoingWorkoutId")
    }

    override fun getSessionPreferences(): Flow<SessionPreferences> {
        return dataStore.data.map {
            mapToSessionPreferences(it)
        }
    }

    override suspend fun removeOngoingWorkout() {

        Log.d(TAG, "remove()")
        dataStore.edit { preferences ->
            preferences[Keys.existsOngoingWorkout] = false
            preferences[Keys.ongoingWorkoutId] = 0
        }
    }

    override suspend fun addOngoingWorkout(id: Int) {
        dataStore.edit { preferences ->
            Log.d(TAG, "addOngoingWorkout()")
            preferences[Keys.existsOngoingWorkout] = true
            preferences[Keys.ongoingWorkoutId] = id
        }
    }

    private fun mapToSessionPreferences(preferences: Preferences): SessionPreferences {
        val existsOngoingWorkoutSession: Boolean = preferences[Keys.existsOngoingWorkout] ?: false
        val ongoingWorkoutId: Int = preferences[Keys.ongoingWorkoutId] ?: 0

        return SessionPreferences(
            existsOngoingWorkout = existsOngoingWorkoutSession,
            ongoingWorkoutId = ongoingWorkoutId
        )
    }

}