package com.example.fitnesstracker.data.models

import java.time.LocalDateTime

data class Workout(
    val id: Int,
    val parentTemplateId: Int,
    val name: String,
    val isBaseTemplate: Boolean,
    val timeStarted: LocalDateTime = LocalDateTime.MIN,
    val duration: Long = 0,
) {


    companion object {
        fun default() = Workout(
            id = 0,
            parentTemplateId = 0,
            name = "default template",
            isBaseTemplate = false,
            timeStarted = LocalDateTime.now(),
            duration = 0
        )
    }
}
