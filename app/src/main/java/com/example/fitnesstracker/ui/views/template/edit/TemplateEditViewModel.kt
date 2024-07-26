package com.example.fitnesstracker.ui.views.template.edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
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

private const val TAG = "TemplateEditViewModel"

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

    fun addSet(exerciseId: Int) {
        _templateDetailed.update { templateDetailed ->
            val updatedExercises = templateDetailed.exercisesWithSetsAndMuscles.map { exerciseDetailed ->
                if (exerciseDetailed.exercise.id == exerciseId) {
                    // Create a new list of sets with the new set added
                    val newSet = exerciseDetailed.sets.last().copy(
                        index = exerciseDetailed.sets.last().index + 1,
                        id = 0
                    )
                    val updatedSets = exerciseDetailed.sets + newSet
                    exerciseDetailed.copy(sets = updatedSets)
                } else {
                    exerciseDetailed
                }
            }
            templateDetailed.copy(
                exercisesWithSetsAndMuscles = updatedExercises
            )
        }
    }

    fun removeSet(exerciseId: Int, setIndex: Int) {
        _templateDetailed.update { templateDetailed ->
            val updatedExercises = templateDetailed.exercisesWithSetsAndMuscles.map { exerciseDetailed ->
                if (exerciseDetailed.exercise.id == exerciseId) {
                    val updatedSets = exerciseDetailed.sets.filter {
                        it.index != setIndex
                    }.toMutableList()
                    for(i in 0 until updatedSets.size) {
                        updatedSets[i] = updatedSets[i].copy(index = i + 1)
                    }
                    exerciseDetailed.copy(sets = updatedSets)
                } else {
                    exerciseDetailed
                }
            }
            templateDetailed.copy(
                exercisesWithSetsAndMuscles = updatedExercises
            )
        }
    }

    fun addExercise(exerciseId: Int) {
        viewModelScope.launch {
            templateService.addExerciseToTemplate(templateId, exerciseId)
        }
    }
}