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

    private lateinit var _ongoingWorkout: StateFlow<WorkoutState>
    val ongoingWorkout get() = _ongoingWorkout

    private val _setStyleMapFlow: MutableStateFlow<Map<Int, SetRowStyle>> = MutableStateFlow(emptyMap())


    private lateinit var progressTracker: ProgressTracker

    private val startTime = LocalDateTime.now()
    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime get() = _elapsedTime
    private val timerRunning = true


    init {
        initializeState()
        initializeProgressTracker()
        runTimer()
    }

    private fun initializeState() {
        _ongoingWorkout = combine(
                workoutService.getTemplateWithExercisesById(workoutId),
                _setStyleMapFlow
            ) { workout, map ->
                val updatedExercises = workout.exercisesWithSetsAndMuscles.map { exercise ->
                    val updatedSets = exercise.sets.map { set ->
                        if(map[set.id] != null) {
                            set.toSetState(map[set.id]!!)
                        }
                        else {
                            set.toSetState(SetRowStyle.DISABLED)
                        }
                    }
                    exercise.toExerciseCardState().copy(
                        sets = updatedSets
                    )
                }
                workout.toWorkoutState().copy(
                    exerciseCardStates = updatedExercises
                )
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WorkoutState.default())
    }

    private fun initializeProgressTracker() {
        progressTracker = ProgressTracker(
            state = ongoingWorkout,
            scope = viewModelScope,
            updateSetStyle = { id, style-> updateSetStyle(id, style)}
        )
    }


    private fun runTimer() {
        viewModelScope.launch {
            while (timerRunning) {
                delay(1000)
                _elapsedTime.update { _elapsedTime.value + 1 }
            }
        }
    }

    private fun updateSetStyle(id: Int, style: SetRowStyle) {
        _setStyleMapFlow.update { map ->
            map.toMutableMap().apply {
                put(id, style)
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

    fun completeSet() {
        progressTracker.completeSet()
    }
}