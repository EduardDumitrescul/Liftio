package com.example.fitnesstracker.data.models

data class Exercise(
    val id: Int,
    var name: String,
    val description: String,
    val equipment: String,
) {
    companion object {
        fun default() = Exercise(
            id = 0,
            name = "",
            description = "",
            equipment = "none"
        )
    }
}