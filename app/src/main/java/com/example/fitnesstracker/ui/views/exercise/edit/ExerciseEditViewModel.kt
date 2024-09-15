package com.example.fitnesstracker.ui.views.exercise.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.services.ExerciseService
import com.example.fitnesstracker.services.MuscleService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExerciseEditViewModel @Inject constructor(
    private val exerciseId: Int,
    private val exerciseService: ExerciseService,
    muscleService: MuscleService,
): ViewModel() {

    private var _exerciseWithMuscles: MutableStateFlow<ExerciseWithMuscles> = MutableStateFlow(
        ExerciseWithMuscles(
            exercise = Exercise.default(),
            primaryMuscle = "",
            secondaryMuscles = listOf()
        )
    )

    val exerciseWithMuscles: StateFlow<ExerciseWithMuscles> = _exerciseWithMuscles.asStateFlow()

    init {
        fetchExerciseWithMuscles()
    }

    private fun fetchExerciseWithMuscles() {
        viewModelScope.launch {
            if(exerciseId != 0)
            exerciseService.getExerciseWithMuscles(exerciseId)
                .collect { fetchedExerciseWithMuscles ->
                    _exerciseWithMuscles.value = fetchedExerciseWithMuscles
                }
        }
    }

    private var _muscleNames: StateFlow<List<String>> = muscleService
        .getMuscleNames()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(0), listOf())
    val muscleNames: StateFlow<List<String>> = _muscleNames

    fun updateExerciseName(newName: String) {
        _exerciseWithMuscles.update { currentState ->
            currentState.copy(exercise = currentState.exercise.copy(name = newName))
        }
    }

    fun updateExerciseDescription(newDescription: String) {
        _exerciseWithMuscles.update { currentState ->
            currentState.copy(exercise = currentState.exercise.copy(description = newDescription))
        }
    }

    fun updatePrimaryMuscle(newPrimaryMuscle: String) {
        _exerciseWithMuscles.update { currentState ->
            currentState.copy(
                primaryMuscle = newPrimaryMuscle,
                secondaryMuscles = currentState.secondaryMuscles.filter { it != newPrimaryMuscle }
            )
        }
    }

    fun updateSecondaryMuscles(newSecondaryMuscles: Set<String>) {
        _exerciseWithMuscles.update { currentState ->
            currentState.copy(secondaryMuscles = newSecondaryMuscles.minus(currentState.primaryMuscle).toList())
        }
    }

    fun updateEquipment(newEquipment: String) {
        _exerciseWithMuscles.update { currentState ->
            currentState.copy(exercise = currentState.exercise.copy(equipment = newEquipment))
        }
    }

    fun save() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            if (_exerciseWithMuscles.value.exercise.id == 0) {
                exerciseService.add(_exerciseWithMuscles.value)
            } else {
                exerciseService.updateExercise(_exerciseWithMuscles.value)
            }
        }
    }
}
