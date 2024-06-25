package com.example.fitnesstracker.repositories

import com.example.fitnesstracker.view.exercise.EquipmentType
import com.example.fitnesstracker.view.exercise.ExerciseSummaryDTO
import kotlinx.coroutines.flow.MutableStateFlow

class ExerciseRepository {
    private var exerciseSummaries = MutableStateFlow(listOf(
        ExerciseSummaryDTO(EquipmentType.Barbell, "Bench Press", "chest", listOf("shoulders", "triceps")),
        ExerciseSummaryDTO(EquipmentType.Dumbbell, "Bicep Curl", "biceps", listOf("forearms")),
        ExerciseSummaryDTO(EquipmentType.Barbell, "Deadlift", "Hamstring", listOf("glutes", "back", "traps")),
    ))

    public fun getExerciseSummaries(exerciseName: String = ""): MutableStateFlow<List<ExerciseSummaryDTO>> {
        return exerciseSummaries
    }
}