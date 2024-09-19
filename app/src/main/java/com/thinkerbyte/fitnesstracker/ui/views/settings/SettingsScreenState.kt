package com.thinkerbyte.fitnesstracker.ui.views.settings

import com.thinkerbyte.fitnesstracker.data.datastore.Theme

data class SettingsScreenState(
    val theme: Theme
) {
    companion object {
        fun default() = SettingsScreenState(
            theme = Theme.SYSTEM
        )
    }
}