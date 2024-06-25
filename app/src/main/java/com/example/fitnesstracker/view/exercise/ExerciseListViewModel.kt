package com.example.fitnesstracker.view.exercise

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.repositories.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

private const val TAG = "ExerciseListViewModel"

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    exerciseRepository: ExerciseRepository
): ViewModel() {

    private var _exerciseSummaries: StateFlow<List<ExerciseSummaryDTO>> = exerciseRepository.getExerciseSummaries()
    private var _filteredExerciseSummaries: MutableStateFlow<List<ExerciseSummaryDTO>> = MutableStateFlow(_exerciseSummaries.value)

    val filteredExerciseSummaries
        get() = _filteredExerciseSummaries

    fun updateFilter(filter: String) {
        _filteredExerciseSummaries.value = _exerciseSummaries.value.filter { it.exerciseName.contains(filter, ignoreCase = true) }
        Log.d(TAG, _exerciseSummaries.value.size.toString())
    }

}
