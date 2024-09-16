package com.example.fitnesstracker.ui.views.workout.perform

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.datastore.SessionPreferences
import com.example.fitnesstracker.services.SessionService
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

private const val TAG = "WorkoutOngoingViewModel"

@HiltViewModel
class WorkoutOngoingViewModel @Inject constructor(
    private val workoutId: Int,
    private val workoutService: WorkoutService,
    private val sessionService: SessionService
): ViewModel() {

    private var _ongoingWorkout: MutableStateFlow<WorkoutState> = MutableStateFlow(WorkoutState.default())
    val ongoingWorkout get() = _ongoingWorkout

    private val sessionPreferences: Flow<SessionPreferences> = sessionService.getSessionPreferences()

    private val _exerciseEndReachedFlow = MutableStateFlow(false)
    val exerciseEndReachedFlow: StateFlow<Boolean> get() = _exerciseEndReachedFlow

    private val timerRunning = true

    init {
        initializeState()
        runTimer()
    }

    private fun initializeState() {
        viewModelScope.launch {
            combine(
                workoutService.getDetailedWorkout(workoutId),
                sessionPreferences
            ) { workout, preferences ->
                Pair(workout, preferences)
            }.collect { (workout, preferences) ->
                val currentExerciseIndex = preferences.exercisesCompleted
                val currentSetIndex = preferences.setsCompleted
                if(workout.detailedExercises[currentExerciseIndex].sets.size == currentSetIndex) {
                    _exerciseEndReachedFlow.update { true }
                }
                else {
                    _exerciseEndReachedFlow.update { false }
                }
                val updatedExercises =
                    workout.detailedExercises.mapIndexed { exerciseIndex, exercise ->
                        val updatedSets = exercise.sets.mapIndexed() { setIndex, set ->
                            if (exerciseIndex < preferences.exercisesCompleted) {
                                set.toSetState(Progress.DONE)
                            } else if (exerciseIndex == currentExerciseIndex && setIndex < currentSetIndex) {
                                set.toSetState(Progress.DONE)
                            } else if (exerciseIndex == currentExerciseIndex && setIndex == currentSetIndex) {
                                set.toSetState(Progress.ONGOING)
                            } else {
                                set.toSetState(Progress.TODO)
                            }
                        }
                        exercise.toExerciseCardState(
                            progress = if (exerciseIndex < currentExerciseIndex) Progress.DONE else Progress.TODO
                        ).copy(
                            sets = updatedSets
                        )
                    }
                _ongoingWorkout.update {
                    workout.toWorkoutState().copy(
                        exerciseCardStates = updatedExercises,
                        duration = Duration.between(workout.timeStarted, LocalDateTime.now()).seconds
                    )
                }
            }
        }
    }


    private fun runTimer() {
        viewModelScope.launch {
            while (timerRunning) {
                delay(1000)
                sessionService.updateDuration(sessionPreferences.first().duration + 1)
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
        viewModelScope.launch {
            sessionService.completeSet()
        }
    }

    fun completeExercise() {
        viewModelScope.launch {
            sessionService.completeExercise()
        }
    }

    fun skipSet() {
        try {
            viewModelScope.launch {
                val id = ongoingWorkout
                    .value
                    .exerciseCardStates[sessionPreferences.first().exercisesCompleted]
                    .sets[sessionPreferences.first().setsCompleted]
                    .id
                workoutService.removeSetFromWorkoutExercise(id)
            }
        }
        catch (ignored: Exception) {}

    }

    fun finishWorkout() {
        viewModelScope.launch {
            removeUncompletedSets()
            removeEmptyExercises()
            workoutService.saveCompletedWorkout(ongoingWorkout.value.toDetailedWorkout())
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