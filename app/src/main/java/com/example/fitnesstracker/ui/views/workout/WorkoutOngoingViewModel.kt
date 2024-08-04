package com.example.fitnesstracker.ui.views.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.services.WorkoutService
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetRowStyle
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.toSetState
import com.example.fitnesstracker.ui.components.exerciseCard.toExerciseCardState
import com.example.fitnesstracker.ui.views.template.detail.WorkoutState
import com.example.fitnesstracker.ui.views.template.detail.toWorkoutState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WorkoutOngoingViewModel @Inject constructor(
    private val workoutId: Int,
    private val workoutService: WorkoutService
): ViewModel() {
    private val rowStyleMapFlow: MutableStateFlow<Map<Int, SetRowStyle>> = MutableStateFlow(emptyMap())

    private lateinit var _ongoingWorkout: StateFlow<WorkoutState>
    val ongoingWorkout get() = _ongoingWorkout

    private val startTime = LocalDateTime.now()
    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime get() = _elapsedTime
    private val timerRunning = true

    init {
        fetchData()
        runTimer()
    }

    private fun fetchData() {
        val templateFlow = workoutService.getTemplateWithExercisesById(workoutId)
        val flow = templateFlow.combine(rowStyleMapFlow) { templateDetailed, rowStyleMap ->
            templateDetailed
                .toWorkoutState()
                .copy(
                    exerciseCardStates = templateDetailed.exercisesWithSetsAndMuscles.map { detailedExercise ->
                        detailedExercise.toExerciseCardState().copy(
                            sets = detailedExercise.sets.map {
                                it.toSetState(rowStyleMap[it.id] ?: SetRowStyle.DISABLED)
                            }
                        )
                    }
                )
        }
        _ongoingWorkout = flow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WorkoutState.default())
    }


    private fun runTimer() {
        viewModelScope.launch {
            while (timerRunning) {
                delay(1000)
                _elapsedTime.update { _elapsedTime.value + 1 }
            }
        }
    }

    fun updateSet(set: SetState) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                workoutService.updateSet(set.toExerciseSet())
            }
        }
    }

    fun addSet(workoutExerciseCrossRefId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                workoutService.addSetToWorkoutExercise(workoutExerciseCrossRefId)
            }
        }
    }

    fun removeSet(workoutExerciseCrossRefId: Int, setId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                workoutService.removeSetFromWorkoutExercise(workoutExerciseCrossRefId, setId)
            }
        }
    }

    fun removeExerciseFromWorkout(workoutExerciseCrossRefId: Int) {
        viewModelScope.launch {
            workoutService.removeExerciseFromTemplate(workoutExerciseCrossRefId)
        }
    }
}