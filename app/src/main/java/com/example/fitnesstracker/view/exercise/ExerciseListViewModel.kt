package com.example.fitnesstracker.view.exercise

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExerciseListViewModel: ViewModel() {
    private var _state: MutableStateFlow<State> = MutableStateFlow(State(listOf()))

    val state = _state.asStateFlow()

    init {
        _state.value.exerciseSummaries = listOf(
            ExerciseSummaryDTO(EquipmentType.Barbell, "Bench Press", "chest", listOf("shoulders", "triceps")),
            ExerciseSummaryDTO(EquipmentType.Dumbbell, "Bicep Curl", "biceps", listOf("forearms")),
            ExerciseSummaryDTO(EquipmentType.Barbell, "Deadlift", "Hamstring", listOf("glutes", "back", "traps")),
        )
    }


}

data class State(
    var exerciseSummaries: List<ExerciseSummaryDTO>
)
