package com.example.fitnesstracker.ui.views.workout.perform

import android.util.Log
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetStatus
import com.example.fitnesstracker.ui.views.workout.detail.WorkoutState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "ProgressTracker"

class ProgressTracker(
    private val state: StateFlow<WorkoutState>,
    private val scope: CoroutineScope,
    private val updateSetStatus: (Int, SetStatus) -> Unit,
) {
    private var _currentSetId = 0
    val currentSetId get() = _currentSetId

    init {
        initializeStatus()
        observeStateUpdates()
    }

    private fun initializeStatus() {
        scope.launch {
            state.collect { state ->
                val ids = state.getAllSetIds()
                if(ids.isNotEmpty()) {
                    ids.forEach { markTodo(it) }
                    _currentSetId = ids.first()
                    markOngoing(_currentSetId)
                    this.cancel()
                }
            }
        }
    }

    private fun observeStateUpdates() {
        scope.launch {
            state.collect {state ->
                val sets = state.getAllSets()
                if(sets.find { it.id == _currentSetId && it.status == SetStatus.ONGOING } == null ) {
                    val firstTodoSet = sets.find { it.status == SetStatus.TODO }
                    _currentSetId = firstTodoSet?.id ?: 0
                    markOngoing(_currentSetId)
                }
            }
        }
    }

    private fun markOngoing(id: Int) {
        updateSetStatus(id, SetStatus.ONGOING)
    }
    private fun markTodo(id: Int) {
        updateSetStatus(id, SetStatus.TODO)
    }
    private fun markDone(id: Int) {
        updateSetStatus(id, SetStatus.DONE)
    }

    fun completeSet() {
        markDone(_currentSetId)
    }



    val currentWorkoutExerciseId: Int get() = run {

        for(exercise in state.value.exerciseCardStates) {
            if(exercise.sets.find { it.id == _currentSetId } != null) {
                return exercise.workoutExerciseCrossRefId
            }
        }
        _currentSetId = 0
        Log.d(TAG, "ASDASDASDASD")
        for(exercise in state.value.exerciseCardStates) {
            if(exercise.sets.find { it.status != SetStatus.DONE } != null
                || exercise.sets.isEmpty()) {
                return exercise.workoutExerciseCrossRefId
            }
        }
        try {
            return state.value.exerciseCardStates.first().workoutExerciseCrossRefId
        }
        catch (ex: NoSuchElementException) {
            return 0
        }
    }
}

private fun WorkoutState.getAllSetIds(): List<Int> {
    return exerciseCardStates.flatMap { it.sets.map { set -> set.id } }
}

private fun WorkoutState.getAllSets(): List<SetState> {
    return exerciseCardStates.flatMap { it.sets }
}