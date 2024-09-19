package com.thinkerbyte.fitnesstracker.ui.views.workout

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class SetEditController {
    private var _currentSetId: MutableStateFlow<Int> = MutableStateFlow(0)
//    val currentSetId: StateFlow<Int> get() = _currentSetId.asStateFlow()

    fun isEditable(id: Int): Flow<Boolean> {
        return _currentSetId.map { it == id }
    }

    fun tryEditing(id: Int) {
        if(_currentSetId.value == 0) {
            _currentSetId.update { id }
        }
    }

    fun stopEditing(id: Int) {
        if(id == _currentSetId.value) {
            _currentSetId.update { 0 }
        }
    }
}