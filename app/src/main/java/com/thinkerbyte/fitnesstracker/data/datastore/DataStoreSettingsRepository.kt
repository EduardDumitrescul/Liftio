package com.thinkerbyte.fitnesstracker.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.thinkerbyte.fitnesstracker.data.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val TAG = "DataStoreSettingsRepository"

class DataStoreSettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    private object Keys {
        val THEME = stringPreferencesKey("theme")
    }

    override fun getUserPreferences(): Flow<UserPreferences> {
        return dataStore.data.catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    Log.e(TAG, "Error reading preferences.", exception)
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                mapUserPreferences(preferences)
            }
    }

    override suspend fun updateTheme(value: Theme) {
        dataStore.edit { preferences ->
            preferences[Keys.THEME] = value.name
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val theme = Theme.valueOf(preferences[Keys.THEME] ?: Theme.SYSTEM.name)
        return UserPreferences(
            theme = theme
        )
    }
}