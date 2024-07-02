package com.example.fitnesstracker.ui.views.exercise.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.ExerciseSummary
import com.example.fitnesstracker.services.ExerciseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ExerciseListViewModel"

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    exerciseService: ExerciseService
): ViewModel() {

    private var _exerciseSummaries: StateFlow<List<ExerciseSummary>> = MutableStateFlow(listOf())
    private var _filteredExerciseSummaries: MutableStateFlow<List<ExerciseSummary>> = MutableStateFlow(_exerciseSummaries.value)

    val filteredExerciseSummaries: StateFlow<List<ExerciseSummary>>
        get() = _filteredExerciseSummaries

    init {
        viewModelScope.launch {
            _exerciseSummaries = MutableStateFlow(exerciseService.getExerciseSummaries())
            _filteredExerciseSummaries.value  = _exerciseSummaries.value
            _exerciseSummaries.collect { summaries ->
                _filteredExerciseSummaries.value = summaries
            }
        }
    }

    fun updateFilter(filter: String) {
        _filteredExerciseSummaries.value = _exerciseSummaries.value.filter { it.exerciseName.contains(filter, ignoreCase = true) }
    }

}
