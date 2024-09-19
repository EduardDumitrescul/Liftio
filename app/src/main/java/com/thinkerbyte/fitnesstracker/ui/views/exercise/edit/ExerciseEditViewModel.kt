package com.thinkerbyte.fitnesstracker.ui.views.exercise.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkerbyte.fitnesstracker.data.dto.ExerciseWithMuscles
import com.thinkerbyte.fitnesstracker.data.models.Exercise
import com.thinkerbyte.fitnesstracker.data.models.Muscle
import com.thinkerbyte.fitnesstracker.services.ExerciseService
import com.thinkerbyte.fitnesstracker.services.MuscleService
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
    private var _muscles: StateFlow<List<Muscle>> = muscleService
        .getMuscles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(0), listOf())
    val muscles: StateFlow<List<Muscle>> = _muscles

    private var _exerciseWithMuscles: MutableStateFlow<ExerciseWithMuscles> = MutableStateFlow(
        ExerciseWithMuscles(
            exercise = Exercise.default(),
            primaryMuscle = Muscle(0, "chest","upper chest", ""),
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

    fun updateMuscleGroup(muscleGroup: String) {
        _exerciseWithMuscles.update { exerciseWithMuscles ->
            exerciseWithMuscles.copy(
                primaryMuscle = _muscles.value.find { it.group == muscleGroup } ?: Muscle.default(),
                secondaryMuscles = exerciseWithMuscles.secondaryMuscles.filter { it.group != muscleGroup }
            )
        }
    }

    fun updateFocusMuscle(focusMuscle: String) {
        _exerciseWithMuscles.update { currentState ->
            currentState.copy(
                primaryMuscle = _muscles.value.find { it.name ==  focusMuscle} ?: Muscle.default()
            )
        }
    }

    fun updateSecondaryMuscles(newSecondaryMuscles: Set<String>) {
        _exerciseWithMuscles.update { currentState ->
            currentState.copy(
                secondaryMuscles = newSecondaryMuscles
                    .map { string -> muscles.value.find { it.name == string }!! }
                    .filter { it.group != currentState.primaryMuscle.group }
                    .minus(currentState.primaryMuscle)
                    .toList()
            )
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
