package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.datastore.Theme
import com.example.fitnesstracker.data.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getUserPreferences(): Flow<UserPreferences>
    suspend fun updateTheme(value: Theme)
}