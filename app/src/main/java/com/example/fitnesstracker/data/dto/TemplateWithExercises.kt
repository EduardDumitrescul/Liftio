package com.example.fitnesstracker.data.dto

import com.example.fitnesstracker.data.models.Template

data class TemplateWithExercises (
    val template: Template,
    val exercisesWithSetsAndMuscles: List<ExerciseWithSetsAndMuscles>,
) {
    companion object {
        fun default() = TemplateWithExercises(
            template = Template.default(),
            exercisesWithSetsAndMuscles = emptyList(),
        )
    }
}