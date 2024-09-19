package com.thinkerbyte.fitnesstracker.ui.views.workout.perform

import android.util.Log
import com.thinkerbyte.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.thinkerbyte.fitnesstracker.ui.components.exerciseCard.Progress
import com.thinkerbyte.fitnesstracker.ui.views.workout.WorkoutState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "ProgressTracker"

class ProgressTracker(
    private val state: StateFlow<WorkoutState>,
    private val scope: CoroutineScope,
    private val updateSetStatus: (Int, Progress) -> Unit,
    private val updateExerciseStatus: (Int, Progress) -> Unit,
    private val updateExerciseEndReached: (Boolean) -> Unit,
) {
    private var _currentSetId = Int.MIN_VALUE
    val currentSetId get() = _currentSetId

    private var _currentExerciseId = Int.MIN_VALUE

    private var _setsCompleted: Int = 0
    val setsCompleted: Int get() = _setsCompleted
    private var _exercisesCompleted: Int = 0
    val exercisesCompleted: Int get() = _exercisesCompleted

    init {
        observeStateUpdates()
    }

    private fun observeStateUpdates() {
        scope.launch {
            state.collect {state ->
                updateCurrentExercise(state)
                updateCurrentSet(state)
            }
        }
    }

    private fun updateCurrentExercise(state: WorkoutState) {
        updateExerciseEndReached(false)

        Log.d(TAG, "false")
        val exerciseStates = state.exerciseCardStates
        if(exerciseStates.find { it.workoutExerciseCrossRefId == _currentExerciseId }?.progress == Progress.ONGOING) {
            return
        }
        for(exerciseState in exerciseStates) {
            if(exerciseState.progress == Progress.TODO) {
                _currentExerciseId = exerciseState.workoutExerciseCrossRefId
                updateExerciseStatus(_currentExerciseId, Progress.ONGOING)
                break
            }
        }
    }

    private fun updateCurrentSet(state: WorkoutState) {
        val setStates = state.getAllSets()
        if(setStates.find { it.id == _currentSetId}?.progress == Progress.ONGOING) {
            return
        }
        val currentExerciseSetStates = state.getAllSets(_currentExerciseId)
        for(setState in currentExerciseSetStates) {
            if(setState.progress == Progress.TODO) {
                _currentSetId = setState.id
                updateSetStatus(_currentSetId, Progress.ONGOING)
                return
            }
        }

        Log.d(TAG, "true")
        updateExerciseEndReached(true)
    }

    fun completeSet() {
        _setsCompleted ++
        updateSetStatus(_currentSetId, Progress.DONE)
    }

    fun completeExercise() {
        _exercisesCompleted ++
        _setsCompleted = 0
        updateExerciseStatus(_currentExerciseId, Progress.DONE)
    }
}

private fun WorkoutState.getAllSets(): List<SetState> {
    return exerciseCardStates.flatMap { it.sets }
}

private fun WorkoutState.getAllSets(workoutExerciseId: Int): List<SetState> {
    return exerciseCardStates.find { it.workoutExerciseCrossRefId ==  workoutExerciseId}?.sets ?: emptyList()
}