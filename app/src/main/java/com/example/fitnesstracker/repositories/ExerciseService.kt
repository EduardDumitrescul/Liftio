package com.example.fitnesstracker.repositories

import com.example.fitnesstracker.view.exercise.EquipmentType
import com.example.fitnesstracker.view.exercise.ExerciseSummary
import kotlinx.coroutines.flow.MutableStateFlow

class ExerciseService {
    private var exerciseSummaries = MutableStateFlow(listOf(
        ExerciseSummary(EquipmentType.Barbell, "Bench Press", "chest", listOf("shoulders", "triceps")),
        ExerciseSummary(EquipmentType.Dumbbell, "Bicep Curl", "biceps", listOf("forearms")),
        ExerciseSummary(EquipmentType.Barbell, "Deadlift", "Hamstring", listOf("glutes", "back", "traps")),
    ))

    public fun getExerciseSummaries(exerciseName: String = ""): MutableStateFlow<List<ExerciseSummary>> {
        return exerciseSummaries
    }
}