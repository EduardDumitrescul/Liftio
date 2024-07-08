package com.example.fitnesstracker.ui.views.exercise.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.data.models.Muscle
import com.example.fitnesstracker.services.ExerciseService
import com.example.fitnesstracker.services.MuscleService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "ExerciseEditViewModel"

@HiltViewModel
class ExerciseEditViewModel @Inject constructor(
    val exerciseId: Int,
    private val exerciseService: ExerciseService,
    private val muscleService: MuscleService,
): ViewModel() {

    private var _exerciseWithMuscles: MutableStateFlow<ExerciseWithMuscles> = MutableStateFlow(ExerciseWithMuscles(
        exercise = Exercise(
            id = 0,
            name = "",
            description = "",
            equipment = "",
        ),
        primaryMuscle = "",
        secondaryMuscles = listOf()
    ))
    val exerciseWithMuscles: StateFlow<ExerciseWithMuscles>
        get() = _exerciseWithMuscles

    private var _muscleNames: MutableStateFlow<List<String>> =  MutableStateFlow(listOf())
    val muscleNames: StateFlow<List<String>>
        get() = _muscleNames.asStateFlow()

    init {
        viewModelScope.launch {
            if (exerciseId != 0) {
                val fetchedExercise = exerciseService.getExerciseWithMuscles(exerciseId)
                _exerciseWithMuscles.update { fetchedExercise }
                Log.d(TAG, "Exercise fetched: $fetchedExercise")
            }

            val fetchedMuscleNames = muscleService.getMuscleNames()
            _muscleNames.value = fetchedMuscleNames
            Log.d(TAG, "Muscle names fetched: $fetchedMuscleNames")
        }
    }

    fun updateExerciseName(it: String) {
        _exerciseWithMuscles.value = _exerciseWithMuscles.value.copy(exercise = _exerciseWithMuscles.value.exercise.copy(name = it))
    }

    fun updateExerciseDescription(it: String) {
        _exerciseWithMuscles.value = _exerciseWithMuscles.value.copy(exercise = _exerciseWithMuscles.value.exercise.copy(description = it))
    }

    fun updatePrimaryMuscle(it: String) {
        _exerciseWithMuscles.value = _exerciseWithMuscles.value.copy(
            primaryMuscle = it
        )
        _exerciseWithMuscles.value = _exerciseWithMuscles.value.copy(
            secondaryMuscles = _exerciseWithMuscles.value.secondaryMuscles.minus(_exerciseWithMuscles.value.primaryMuscle)
        )
    }

    fun updateSecondaryMuscles(it: Set<String>) {
        _exerciseWithMuscles.value = _exerciseWithMuscles.value.copy(secondaryMuscles = it.minus(_exerciseWithMuscles.value.primaryMuscle).toList())
    }

    fun save() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            if(_exerciseWithMuscles.value.exercise.id == 0) {
                exerciseService.add(_exerciseWithMuscles.value)
            }
            else {
                exerciseService.updateExercise(_exerciseWithMuscles.value)
            }
        }
    }

    fun updateEquipment(it: String) {
        _exerciseWithMuscles.value = _exerciseWithMuscles.value.copy(
            exercise = _exerciseWithMuscles.value.exercise.copy(equipment = it)
        )
    }

}