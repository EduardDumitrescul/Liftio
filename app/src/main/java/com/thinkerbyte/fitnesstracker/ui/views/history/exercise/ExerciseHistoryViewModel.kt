package com.thinkerbyte.fitnesstracker.ui.views.history.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkerbyte.fitnesstracker.data.dto.DateWithSets
import com.thinkerbyte.fitnesstracker.data.models.Exercise
import com.thinkerbyte.fitnesstracker.services.ExerciseService
import com.thinkerbyte.fitnesstracker.services.HistoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseHistoryViewModel @Inject constructor(
    private val exerciseId: Int,
    private val exerciseService: ExerciseService,
    private val historyService: HistoryService
): ViewModel() {
    private val _state: MutableStateFlow<ExerciseHistoryScreenState> = MutableStateFlow(ExerciseHistoryScreenState.default())
    val state: StateFlow<ExerciseHistoryScreenState> get() = _state.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        val exerciseFlow: Flow<Exercise> = exerciseService.getExercise(exerciseId)
        val setsWithDateFlow: Flow<List<DateWithSets>> = historyService.getSetsWithDate(exerciseId)
        viewModelScope.launch {
            combine(exerciseFlow, setsWithDateFlow) { exerciseFlow, setsWithDateFlow ->
                Pair(exerciseFlow, setsWithDateFlow)
            }.collect{ (exercise, setsWithDate) ->
                _state.update {
                    _state.value.copy(
                        exerciseName = exercise.name,
                        datesWithSets = setsWithDate
                    )
                }
            }
        }
    }
}