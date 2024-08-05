package com.example.fitnesstracker.ui.views.workout

import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetRowStyle
import com.example.fitnesstracker.ui.views.template.detail.WorkoutState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "ProgressTracker"

class ProgressTracker(
    private val state: StateFlow<WorkoutState>,
    private val scope: CoroutineScope,
    private val updateSetStyle: (Int, SetRowStyle) -> Unit,
) {
//    private val _rowStyleMapFlow: MutableStateFlow<Map<Int, SetRowStyle>> = MutableStateFlow(emptyMap())
//    val rowStyleMapFlow: StateFlow<Map<Int, SetRowStyle>> get() = _rowStyleMapFlow

    private var currentSetIndex = 0
    private var currentSetId = 0

    init {
        observeState()
    }

    private fun observeState() {
        scope.launch {
            state.collect { state->
                val ids = state.getAllSetIds()
                if(currentSetIndex < ids.size) {
                    if(currentSetId != ids[currentSetIndex]) {
                        currentSetId = ids[currentSetIndex]
                        highlightSet(currentSetId)
                    }
                }
            }
        }
    }

    private fun highlightSet(id: Int) {
        updateSetStyle(id, SetRowStyle.HIGHLIGHTED)
    }

    fun completeSet() {
        updateSetStyle(currentSetId, SetRowStyle.NORMAL)
        goToNextSet()
    }

    private fun goToNextSet() {
        currentSetIndex += 1
        scope.launch {
            state.collect { state->
                val ids = state.getAllSetIds()
                if(currentSetIndex < ids.size) {
                    if(currentSetId != ids[currentSetIndex]) {
                        currentSetId = ids[currentSetIndex]
                        highlightSet(currentSetId)
                    }
                }
            }
        }
    }

}

private fun WorkoutState.getAllSetIds(): List<Int> {
    return exerciseCardStates.flatMap { it.sets.map { set -> set.id } }
}