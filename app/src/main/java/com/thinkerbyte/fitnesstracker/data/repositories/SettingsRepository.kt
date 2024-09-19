package com.thinkerbyte.fitnesstracker.data.repositories

import com.thinkerbyte.fitnesstracker.data.datastore.Theme
import com.thinkerbyte.fitnesstracker.data.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getUserPreferences(): Flow<UserPreferences>
    suspend fun updateTheme(value: Theme)
}