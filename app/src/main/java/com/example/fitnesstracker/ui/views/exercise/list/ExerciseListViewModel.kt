package com.example.fitnesstracker.ui.views.exercise.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.services.ExerciseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ExerciseListViewModel"

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    exerciseService: ExerciseService
): ViewModel() {

    private var _exerciseWithMuscles: StateFlow<List<ExerciseWithMuscles>> = MutableStateFlow(listOf())
    private var _filteredExerciseSummaries: MutableStateFlow<List<ExerciseWithMuscles>> = MutableStateFlow(_exerciseWithMuscles.value)

    val filteredExerciseSummaries: StateFlow<List<ExerciseWithMuscles>>
        get() = _filteredExerciseSummaries

    init {
        viewModelScope.launch {
            _exerciseWithMuscles = MutableStateFlow(exerciseService.getExerciseSummaries())
            _filteredExerciseSummaries.value  = _exerciseWithMuscles.value
            _exerciseWithMuscles.collect { summaries ->
                _filteredExerciseSummaries.value = summaries
            }
        }
    }

    fun updateFilter(filter: String) {
        _filteredExerciseSummaries.value = _exerciseWithMuscles.value.filter { it.exercise.name.contains(filter, ignoreCase = true) }
    }

}
