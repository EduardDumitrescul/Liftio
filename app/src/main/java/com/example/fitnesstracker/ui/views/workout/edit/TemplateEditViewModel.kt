package com.example.fitnesstracker.ui.views.workout.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.services.WorkoutService
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.example.fitnesstracker.ui.views.workout.detail.WorkoutState
import com.example.fitnesstracker.ui.views.workout.detail.toWorkoutState
import com.example.fitnesstracker.utils.swap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "TemplateEditViewModel"

@HiltViewModel
class TemplateEditViewModel @Inject constructor(
    private var templateId: Int,
    private val workoutService: WorkoutService,
): ViewModel() {
    private val _state = MutableStateFlow(WorkoutState.default())
    val state: StateFlow<WorkoutState> get() = _state

    private var _isNewTemplate: Boolean = false
    val isNewTemplate get() = _isNewTemplate

    private var _wasNameUpdated: Boolean = false
    val wasNameUpdated get() = _wasNameUpdated

    private var collectionJob: Job? = null

    init {
        if(templateId == 0) {
            _isNewTemplate = true
            createNewTemplate()
        }
        else {
            getTemplateData()
        }

    }

    private fun createNewTemplate() {
        collectionJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                templateId = workoutService.createNewTemplate()
                val flow = workoutService.getDetailedWorkout(templateId)

                flow.collect { templateDetailed ->
                    _state.value = templateDetailed.toWorkoutState()
                }
            }

        }
    }

    private fun getTemplateData() {
        viewModelScope.launch {
            workoutService.getDetailedWorkout(templateId)
                .collect { templateDetailed ->
                    _state.value = templateDetailed.toWorkoutState()
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

    fun addSet(templateExerciseCrossRefId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                workoutService.addSetToWorkoutExercise(templateExerciseCrossRefId)
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

    fun addExercise(exerciseId: Int) {
        viewModelScope.launch {
            workoutService.addExerciseToTemplate(templateId, exerciseId)
        }
    }

    fun updateTemplateName(templateName: String) {
        viewModelScope.launch {
            workoutService.updateTemplateName(templateId, templateName)
        }
        _wasNameUpdated = true
    }

    fun removeExerciseFromTemplate(templateExerciseCrossRefId: Int) {
        viewModelScope.launch {
            workoutService.removeExerciseFromWorkout(templateExerciseCrossRefId)
        }
    }

    fun removeTemplate() {
        collectionJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            workoutService.removeTemplate(templateId)
        }
    }

    fun locallyReorderExercises(fromIndex: Int, toIndex: Int) {
        Log.d(TAG, "local")
        _state.update { state ->
            val updatedExerciseCardStates = state.exerciseCardStates.swap(fromIndex, toIndex)
            state.copy(
                exerciseCardStates = updatedExerciseCardStates
            )
        }
    }

    fun saveExercisesOrder() {
        Log.d(TAG, "db")
        viewModelScope.launch {
            val newIndexesForId = state.value.exerciseCardStates.mapIndexed { index, exerciseCardState ->
                Pair(exerciseCardState.workoutExerciseCrossRefId, index)
            }
            workoutService.updateWorkoutExerciseIndexes(newIndexesForId)
        }
    }
}