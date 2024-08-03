package com.example.fitnesstracker.data.models

data class Workout(
    val id: Int,
    val parentTemplateId: Int,
    val name: String,
    val isBaseTemplate: Boolean
) {
    companion object {
        fun default() = Workout(
            id = 0,
            parentTemplateId = 0,
            name = "default template",
            isBaseTemplate = false,
        )
    }
}
