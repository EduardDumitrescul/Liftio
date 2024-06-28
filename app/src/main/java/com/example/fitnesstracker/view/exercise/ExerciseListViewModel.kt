package com.example.fitnesstracker.view.exercise

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.repositories.ExerciseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val TAG = "ExerciseListViewModel"

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    exerciseService: ExerciseService
): ViewModel() {

    private var _exerciseSummaries: StateFlow<List<ExerciseSummary>> = exerciseService.getExerciseSummaries()
    private var _filteredExerciseSummaries: MutableStateFlow<List<ExerciseSummary>> = MutableStateFlow(_exerciseSummaries.value)

    val filteredExerciseSummaries
        get() = _filteredExerciseSummaries

    fun updateFilter(filter: String) {
        _filteredExerciseSummaries.value = _exerciseSummaries.value.filter { it.exerciseName.contains(filter, ignoreCase = true) }
        Log.d(TAG, _exerciseSummaries.value.size.toString())
    }

}
