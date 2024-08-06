package com.example.fitnesstracker.ui.views.workout

import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetStatus
import com.example.fitnesstracker.ui.views.template.detail.WorkoutState
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
        markNextOngoing()
    }

    private fun markNextOngoing() {
        val sets = state.value.getAllSets()
        for(i in 1 until sets.size) {
            if(sets[i-1].id == _currentSetId) {
                _currentSetId = sets[i].id
                markOngoing(_currentSetId)
                break
            }
        }
    }

    fun skipSet() {
        markNextOngoing()
    }
}

private fun WorkoutState.getAllSetIds(): List<Int> {
    return exerciseCardStates.flatMap { it.sets.map { set -> set.id } }
}

private fun WorkoutState.getAllSets(): List<SetState> {
    return exerciseCardStates.flatMap { it.sets }
}