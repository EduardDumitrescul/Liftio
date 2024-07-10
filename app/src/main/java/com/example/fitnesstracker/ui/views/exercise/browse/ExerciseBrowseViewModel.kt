package com.example.fitnesstracker.ui.views.exercise.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
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
class ExerciseBrowseViewModel @Inject constructor(
    exerciseService: ExerciseService
) : ViewModel() {

    val _exerciseWithMuscles: StateFlow<List<ExerciseWithMuscles>> = exerciseService
        .getExercisesWithMuscles()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _filteredExerciseSummaries: MutableStateFlow<List<ExerciseWithMuscles>> = MutableStateFlow(_exerciseWithMuscles.value)

    val filteredExerciseSummaries: StateFlow<List<ExerciseWithMuscles>> = _filteredExerciseSummaries

    init {
        viewModelScope.launch {
            _exerciseWithMuscles.collect { exercises ->
                _filteredExerciseSummaries.value = exercises
            }
        }
    }

    fun updateFilter(filter: String) {
        _filteredExerciseSummaries.value = _exerciseWithMuscles.value.filter {
            it.exercise.name.contains(filter, ignoreCase = true)
        }
    }
}
