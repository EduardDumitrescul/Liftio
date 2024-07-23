package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.data.models.Template

data class TemplateWithExercises (
    val template: Template
) {
    companion object {
        fun default() = TemplateWithExercises(
            template = Template.default()
        )
    }
}