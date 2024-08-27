package com.example.fitnesstracker.ui.views.workout.perform

import android.util.Log
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.example.fitnesstracker.ui.components.exerciseCard.Progress
import com.example.fitnesstracker.ui.views.workout.WorkoutState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "ProgressTracker"

class ProgressTracker(
    private val state: StateFlow<WorkoutState>,
    private val scope: CoroutineScope,
    private val updateSetStatus: (Int, Progress) -> Unit,
) {
    private var _currentSetId = 0
    val currentSetId get() = _currentSetId

    private var _currentExerciseId = 0
    val currentExerciseId get() = _currentExerciseId

    init {
        initializeStatus()
        observeStateUpdates()
    }

    private fun initializeStatus() {
        scope.launch {
            state.collect { state ->
                _currentExerciseId = state.getFirstExerciseId()
                _currentSetId = state.getFirstSetId()

                markOngoing(_currentSetId)
                cancel()
            }
        }
    }

    private fun observeStateUpdates() {
        scope.launch {
            state.collect {state ->

                val sets = state.getAllSets()
                if(sets.find { it.id == _currentSetId && it.status == Progress.ONGOING } == null ) {
                    val firstTodoSet = sets.find { it.status == Progress.TODO }
                    _currentSetId = firstTodoSet?.id ?: 0
                    markOngoing(_currentSetId)
                }
            }
        }
    }

    private fun markOngoing(id: Int) {
        updateSetStatus(id, Progress.ONGOING)
    }
    private fun markTodo(id: Int) {
        updateSetStatus(id, Progress.TODO)
    }
    private fun markDone(id: Int) {
        updateSetStatus(id, Progress.DONE)
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
            if(exercise.sets.find { it.status != Progress.DONE } != null
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

private fun WorkoutState.getFirstSetId(): Int {
    return try {
        this.getAllSetIds()[0]
    } catch (exception: IndexOutOfBoundsException) {
        0
    }
}

private fun WorkoutState.getFirstExerciseId(): Int {
    if(this.exerciseCardStates.isEmpty()) {
        return 0
    }
    return this.exerciseCardStates[0].workoutExerciseCrossRefId
}

private fun WorkoutState.getAllSetIds(): List<Int> {
    return exerciseCardStates.flatMap { it.sets.map { set -> set.id } }
}

private fun WorkoutState.getAllSets(): List<SetState> {
    return exerciseCardStates.flatMap { it.sets }
}