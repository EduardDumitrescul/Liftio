package com.example.fitnesstracker.ui.views.workout.perform

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.services.WorkoutService
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.example.fitnesstracker.ui.components.exerciseCard.Progress
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.toSetState
import com.example.fitnesstracker.ui.components.exerciseCard.toExerciseCardState
import com.example.fitnesstracker.ui.views.workout.WorkoutState
import com.example.fitnesstracker.ui.views.workout.toDetailedWorkout
import com.example.fitnesstracker.ui.views.workout.toWorkoutState
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

private const val TAG = "WorkoutOngoingViewModel"

@HiltViewModel
class WorkoutOngoingViewModel @Inject constructor(
    private val workoutId: Int,
    private val workoutService: WorkoutService
): ViewModel() {

    private lateinit var _ongoingWorkout: StateFlow<WorkoutState>
    val ongoingWorkout get() = _ongoingWorkout

    //TODO rename
    private val _setStyleMapFlow: MutableStateFlow<Map<Int, Progress>> = MutableStateFlow(emptyMap())

    private val _exerciseProgressMapFlow: MutableStateFlow<Map<Int, Progress>> = MutableStateFlow(emptyMap())


    private lateinit var progressTracker: ProgressTracker
    private val _exerciseEndReachedFlow = MutableStateFlow(false)
    val exerciseEndReachedFlow: StateFlow<Boolean> get() = _exerciseEndReachedFlow

    private val _startTime = LocalDateTime.now()
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
                workoutService.getDetailedWorkout(workoutId),
                _setStyleMapFlow,
                _exerciseProgressMapFlow
            ) { workout, setProgressMap, exerciseProgressMap ->
                val updatedExercises = workout.detailedExercises.map { exercise ->
                    val updatedSets = exercise.sets.map { set ->
                        if(setProgressMap.containsKey(set.id)) {
                            set.toSetState(setProgressMap[set.id]!!)
                        }
                        else {
                            set.toSetState(Progress.TODO)
                        }
                    }
                    exercise.toExerciseCardState(
                        progress = exerciseProgressMap[exercise.templateExerciseCrossRefId] ?: Progress.TODO
                    ).copy(
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
            updateSetStatus = { id, status-> updateSetStatus(id, status)},
            updateExerciseStatus = { id, status -> updateExerciseStatus(id, status)},
            updateExerciseEndReached = { value -> _exerciseEndReachedFlow.update { value } }
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

    private fun updateSetStatus(id: Int, status: Progress) {
        _setStyleMapFlow.update { map ->
            map.toMutableMap().apply {
                put(id, status)
            }
        }
    }

    private fun updateExerciseStatus(id: Int, status: Progress) {
        _exerciseProgressMapFlow.update { map ->
            map.toMutableMap().apply {
                put(id, status)
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

    fun removeSet(setId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                workoutService.removeSetFromWorkoutExercise(setId)
            }
        }
    }

    fun removeExerciseFromWorkout(workoutExerciseCrossRefId: Int) {
        viewModelScope.launch {
            workoutService.removeExerciseFromWorkout(workoutExerciseCrossRefId)
        }
    }

    fun completeSet() {
        progressTracker.completeSet()
    }

    fun completeExercise() {
        progressTracker.completeExercise()
    }

    fun skipSet() {
        val id = progressTracker.currentSetId
        viewModelScope.launch {
            workoutService.removeSetFromWorkoutExercise(id)
        }
    }

    fun finishWorkout() {
        viewModelScope.launch {
            removeUncompletedSets()
            removeEmptyExercises()
            workoutService.saveCompletedWorkout(_ongoingWorkout.value.toDetailedWorkout(
                timeStarted = _startTime,
                duration = _elapsedTime.value)
            )
        }
    }

    private suspend fun removeUncompletedSets() {
        for(exerciseCardState in _ongoingWorkout.value.exerciseCardStates) {
            for(set in exerciseCardState.sets) {
                if(set.progress != Progress.DONE) {
                    workoutService.removeSetFromWorkoutExercise(set.id)
                }
            }
        }
    }

    private suspend fun removeEmptyExercises() {
        for(exerciseCardState in _ongoingWorkout.value.exerciseCardStates) {
            if(!exerciseCardState.hasSets()) {
                workoutService.removeExerciseFromWorkout(exerciseCardState.workoutExerciseCrossRefId)
            }
        }
    }

    fun addExercise(exerciseId: Int) {
        viewModelScope.launch {
            workoutService.addExerciseToTemplate(workoutId, exerciseId)
        }
    }
}