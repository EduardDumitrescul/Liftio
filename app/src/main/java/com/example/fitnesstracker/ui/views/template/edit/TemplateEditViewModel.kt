package com.example.fitnesstracker.ui.views.template.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.TemplateDetailed
import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.services.TemplateService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "TemplateEditViewModel"

@HiltViewModel
class TemplateEditViewModel @Inject constructor(
    val templateId: Int,
    private val templateService: TemplateService,
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
    fun updateSet(templateExerciseCrossRefId: Int, set: ExerciseSet) {
        _templateDetailed.update { templateDetailed ->
            val updatedExercises = templateDetailed.exercisesWithSetsAndMuscles.map { exercises ->
                if(exercises.templateExerciseCrossRefId == templateExerciseCrossRefId) {
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

    fun addSet(templateExerciseCrossRefId: Int) {
        _templateDetailed.update { templateDetailed ->
            val updatedExercises = templateDetailed.exercisesWithSetsAndMuscles.map { exerciseDetailed ->
                if (exerciseDetailed.templateExerciseCrossRefId == templateExerciseCrossRefId) {
                    Log.d(TAG, exerciseDetailed.toString())
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

    fun removeSet(templateExerciseCrossRefId: Int, setId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                templateService.removeSetFromTemplateExercise(templateExerciseCrossRefId, setId)
            }
        }


//        _templateDetailed.update { templateDetailed ->
//            val updatedExercises = templateDetailed.exercisesWithSetsAndMuscles.map { exerciseDetailed ->
//                if (exerciseDetailed.exercise.id == exerciseId) {
//                    val updatedSets = exerciseDetailed.sets.filter {
//                        it.index != setIndex
//                    }.toMutableList()
//                    for(i in 0 until updatedSets.size) {
//                        updatedSets[i] = updatedSets[i].copy(index = i + 1)
//                    }
//                    exerciseDetailed.copy(sets = updatedSets)
//                } else {
//                    exerciseDetailed
//                }
//            }
//            templateDetailed.copy(
//                exercisesWithSetsAndMuscles = updatedExercises
//            )
//        }
    }

    fun addExercise(exerciseId: Int) {
        viewModelScope.launch {
            templateService.addExerciseToTemplate(templateId, exerciseId)
        }
    }

    fun updateTemplateName(templateName: String) {
        viewModelScope.launch {
            templateService.updateTemplateName(templateId, templateName)
        }
    }

    fun removeExerciseFromTemplate(templateExerciseCrossRefId: Int) {
        viewModelScope.launch {
            templateService.removeExerciseFromTemplate(templateExerciseCrossRefId)
        }
    }
}