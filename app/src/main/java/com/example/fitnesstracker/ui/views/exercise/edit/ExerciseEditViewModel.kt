package com.example.fitnesstracker.ui.views.exercise.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
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

private const val TAG = "ExerciseEditViewModel"

@HiltViewModel
class ExerciseEditViewModel @Inject constructor(
    val exerciseId: Int,
    private val exerciseService: ExerciseService,
    private val muscleService: MuscleService,
): ViewModel() {

    private var _exercise: MutableStateFlow<ExerciseWithMuscles> = MutableStateFlow(ExerciseWithMuscles(
        exerciseId = 0,
        exerciseName = "",
        exerciseDescription = "",
        equipment = "",
        primaryMuscle = "",
        secondaryMuscles = listOf()
    ))
    val exercise: StateFlow<ExerciseWithMuscles>
        get() = _exercise

    private var _muscleNames: MutableStateFlow<List<String>> =  MutableStateFlow(listOf())
    val muscleNames: StateFlow<List<String>>
        get() = _muscleNames.asStateFlow()

    init {
        viewModelScope.launch {
            if (exerciseId != 0) {
                val fetchedExercise = exerciseService.getExerciseWithMuscles(exerciseId)
                _exercise.update { fetchedExercise }
                Log.d(TAG, "Exercise fetched: $fetchedExercise")
            }

            val fetchedMuscleNames = muscleService.getMuscleNames()
            _muscleNames.value = fetchedMuscleNames
            Log.d(TAG, "Muscle names fetched: $fetchedMuscleNames")
        }
    }

    fun updateExerciseName(it: String) {
        _exercise.value = _exercise.value.copy(exerciseName = it)
    }

    fun updateExerciseDescription(it: String) {
        _exercise.value = _exercise.value.copy(exerciseDescription = it)
    }

    fun updatePrimaryMuscle(it: String) {
        _exercise.value = _exercise.value.copy(primaryMuscle = it)
    }

    fun updateSecondaryMuscles(it: Set<String>) {
        _exercise.value = _exercise.value.copy(secondaryMuscles = it.toList())
    }

    fun save() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            if(_exercise.value.exerciseId == 0) {
                exerciseService.add(_exercise.value)
            }
            else {
                exerciseService.updateExercise(_exercise.value)
            }
        }
    }

    fun updateEquipment(it: String) {
        _exercise.value = _exercise.value.copy(equipment = it)
    }

}