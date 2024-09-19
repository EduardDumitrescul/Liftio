package com.thinkerbyte.fitnesstracker.ui.views.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkerbyte.fitnesstracker.data.dto.WorkoutEntry
import com.thinkerbyte.fitnesstracker.services.HistoryService
import com.thinkerbyte.fitnesstracker.ui.components.workoutCard.asWorkoutEntryCardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutHistoryViewModel @Inject constructor(
    private val historyService: HistoryService
): ViewModel() {
    private val _state : MutableStateFlow<WorkoutHistoryScreenState> = MutableStateFlow(
        WorkoutHistoryScreenState.default()
    )
    val state: StateFlow<WorkoutHistoryScreenState> get() = _state

    init {
        fetchWorkoutEntriesData()
    }

    private fun fetchWorkoutEntriesData() {
        val workoutEntriesFlow: Flow<List<WorkoutEntry>> = historyService.getWorkoutEntries()
        viewModelScope.launch {
            workoutEntriesFlow.collect { workoutEntries ->
                _state.update {
                    WorkoutHistoryScreenState(
                        workoutEntryCardStates = workoutEntries.map {it.asWorkoutEntryCardState()}
                    )
                }
            }
        }
    }
}