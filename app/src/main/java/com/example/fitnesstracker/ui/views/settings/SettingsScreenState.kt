package com.example.fitnesstracker.ui.views.settings

import com.example.fitnesstracker.data.datastore.Theme

data class SettingsScreenState(
    val theme: Theme
) {
    companion object {
        fun default() = SettingsScreenState(
            theme = Theme.SYSTEM
        )
    }
}