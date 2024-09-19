package com.thinkerbyte.fitnesstracker.ui.views.exercise.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkerbyte.fitnesstracker.data.dto.ExerciseWithMuscles
import com.thinkerbyte.fitnesstracker.services.ExerciseService
import com.thinkerbyte.fuzzysearch.FuzzySearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseBrowseViewModel @Inject constructor(
    private val exerciseService: ExerciseService
) : ViewModel() {
    private val _searchValue: MutableStateFlow<String> = MutableStateFlow("")
    val searchValue: StateFlow<String> get() = _searchValue

    private val _exerciseSummaries: MutableStateFlow<List<ExerciseWithMuscles>> = MutableStateFlow(emptyList())

    private val _filteredExerciseSummaries: MutableStateFlow<List<ExerciseWithMuscles>> = MutableStateFlow(emptyList())
    val filteredExerciseSummaries: StateFlow<List<ExerciseWithMuscles>> = _filteredExerciseSummaries

    private var fuzzySearch: FuzzySearch = FuzzySearch(emptyList())

    init {
        fetchExerciseSummaries()
        syncSearchValue()

    }

    private fun fetchExerciseSummaries() {
        viewModelScope.launch {
            exerciseService
                .getExercisesWithMuscles()
                .collect { exercises ->
                    fuzzySearch = FuzzySearch(exercises.map { it.createSearchEntry() })
                    _exerciseSummaries.update { exercises }
                    _filteredExerciseSummaries.update { fuzzySearch.search(searchValue.value).map { exercises[it] }}
                }
        }
    }

    private fun ExerciseWithMuscles.createSearchEntry(): String {
        return "${this.exercise.name} ${this.primaryMuscle.name} ${this.secondaryMuscles.joinToString(" ")}"
    }

    private fun syncSearchValue() {
        viewModelScope.launch {
            _searchValue.collect { searchValue ->
                _filteredExerciseSummaries.value = fuzzySearch.search(searchValue).map { _exerciseSummaries.value[it] }
            }
        }
    }

    fun updateSearchValue(value: String) {
        _searchValue.update { value }
    }
}
