package com.thinkerbyte.fitnesstracker.data.models

import java.time.LocalDateTime

data class Workout(
    val id: Int,
    val parentTemplateId: Int,
    val name: String,
    val isBaseTemplate: Boolean,
    val timeStarted: LocalDateTime,
    val duration: Long,
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
