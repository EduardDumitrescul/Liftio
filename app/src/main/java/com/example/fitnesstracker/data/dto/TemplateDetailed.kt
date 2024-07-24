package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.data.models.Template

data class TemplateDetailed (
    val template: Template,
    val exercisesWithSetsAndMuscles: List<ExerciseDetailed>,
) {
    companion object {
        fun default() = TemplateDetailed(
            template = Template.default(),
            exercisesWithSetsAndMuscles = emptyList(),
        )
    }
}