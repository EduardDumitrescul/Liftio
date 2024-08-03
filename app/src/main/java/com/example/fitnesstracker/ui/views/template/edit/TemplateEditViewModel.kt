package com.example.fitnesstracker.ui.views.template.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.DetailedWorkout
import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.services.WorkoutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "TemplateEditViewModel"

@HiltViewModel
class TemplateEditViewModel @Inject constructor(
    private var templateId: Int,
    private val workoutService: WorkoutService,
): ViewModel() {
    private val _DetailedWorkout = MutableStateFlow(DetailedWorkout.default())
    val detailedWorkout: StateFlow<DetailedWorkout> get() = _DetailedWorkout

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
                val flow = workoutService.getTemplateWithExercisesById(templateId)

                flow.collect { templateDetailed ->
                    _DetailedWorkout.value = templateDetailed
                }
            }

        }
    }

    private fun getTemplateData() {
        viewModelScope.launch {
            workoutService.getTemplateWithExercisesById(templateId)
                .collect { newTemplateDetailed ->
                    _DetailedWorkout.value = newTemplateDetailed
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

    fun addSet(templateExerciseCrossRefId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                workoutService.addSetToWorkoutExercise(templateExerciseCrossRefId)
            }
        }
    }

    fun removeSet(templateExerciseCrossRefId: Int, setId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                workoutService.removeSetFromWorkoutExercise(templateExerciseCrossRefId, setId)
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
            workoutService.removeExerciseFromTemplate(templateExerciseCrossRefId)
        }
    }

    fun removeTemplate() {
        collectionJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            workoutService.removeTemplate(templateId)
        }
    }
}