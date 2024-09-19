package com.thinkerbyte.fitnesstracker.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkerbyte.fitnesstracker.data.datastore.Theme
import com.thinkerbyte.fitnesstracker.data.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    private val _theme: MutableStateFlow<Theme> = MutableStateFlow(Theme.SYSTEM)
    val theme: StateFlow<Theme> get() = _theme.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.getUserPreferences().collect { userPreferences ->
                _theme.value = userPreferences.theme
            }
        }
    }
}