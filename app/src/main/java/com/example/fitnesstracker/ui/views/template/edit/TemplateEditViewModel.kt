package com.example.fitnesstracker.ui.views.template.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.TemplateDetailed
import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.services.SetService
import com.example.fitnesstracker.services.TemplateService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplateEditViewModel @Inject constructor(
    val templateId: Int,
    private val templateService: TemplateService,
    private val setService: SetService,
): ViewModel() {

    private val _templateDetailed = MutableStateFlow(TemplateDetailed.default())
    val templateDetailed: StateFlow<TemplateDetailed> get() = _templateDetailed

    init {
        viewModelScope.launch {
            templateService.getTemplateWithExercisesById(templateId)
                .collect { newTemplateDetailed ->
                    _templateDetailed.value = newTemplateDetailed
                }
        }
    }
    fun updateSet(exerciseId: Int, set: ExerciseSet) {
        _templateDetailed.update { templateDetailed ->
            val updatedExercises = templateDetailed.exercisesWithSetsAndMuscles.map { exercises ->
                if(exercises.exercise.id == exerciseId) {
                    val sets = exercises.sets.map {
                        if(it.id == set.id) {
                            set
                        }
                        else {
                            it
                        }
                    }
                    exercises.copy(sets = sets)
                }
                else {
                    exercises
                }
            }
            templateDetailed.copy(
                exercisesWithSetsAndMuscles = updatedExercises
            )

        }
    }
}