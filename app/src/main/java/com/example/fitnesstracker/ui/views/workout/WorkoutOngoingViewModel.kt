package com.example.fitnesstracker.ui.views.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.DetailedWorkout
import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.services.WorkoutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
    val ongoingWorkout: StateFlow<DetailedWorkout> = workoutService
        .getTemplateWithExercisesById(workoutId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailedWorkout.default())

    private val startTime = LocalDateTime.now()
    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime get() = _elapsedTime
    private val timerRunning = true

    init {
        runTimer()
    }

    private fun runTimer() {
        viewModelScope.launch {
            while (timerRunning) {
                delay(1000)
                _elapsedTime.update { _elapsedTime.value + 1 }
            }
        }
    }

    fun updateSet(set: ExerciseSet) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                workoutService.updateSet(set)
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