package com.thinkerbyte.fitnesstracker.ui.views.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkerbyte.fitnesstracker.data.datastore.Theme
import com.thinkerbyte.fitnesstracker.data.datastore.UserPreferences
import com.thinkerbyte.fitnesstracker.data.repositories.SettingsRepository
import com.thinkerbyte.fitnesstracker.services.HistoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val historyService: HistoryService,
): ViewModel() {
    private val _state: MutableStateFlow<SettingsScreenState> = MutableStateFlow(SettingsScreenState.default())
    val state: StateFlow<SettingsScreenState> get() = _state.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        val userPreferencesFlow: Flow<UserPreferences> = settingsRepository.getUserPreferences()
        viewModelScope.launch {
            userPreferencesFlow.collect { userPreferences ->
                _state.update {
                    _state.value.copy(
                        theme = userPreferences.theme
                    )
                }
            }
        }
    }

    fun updateTheme(value: Theme) {
        viewModelScope.launch {
            settingsRepository.updateTheme(value)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            historyService.clearHistory()
        }
    }

}