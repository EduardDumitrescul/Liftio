package com.thinkerbyte.fitnesstracker.data.datastore

data class UserPreferences(
    val theme: Theme
)

enum class Theme {
    LIGHT,
    DARK,
    SYSTEM
}