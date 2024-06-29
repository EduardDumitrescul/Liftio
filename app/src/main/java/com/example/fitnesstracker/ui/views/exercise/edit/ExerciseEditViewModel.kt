package com.example.fitnesstracker.ui.views.exercise.edit

import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.data.models.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExerciseEditViewModel @Inject constructor(
    val exerciseId: Int
): ViewModel() {

    private var _exercise: MutableStateFlow<ExerciseWithMuscles> = MutableStateFlow(ExerciseWithMuscles(
        exerciseId = 0,
        exerciseName = "",
        exerciseDescription = "",
        primaryMuscle = "",
        secondaryMuscles = listOf()
    ))
    val exercise: StateFlow<ExerciseWithMuscles>
        get() = MutableStateFlow(_exercise.value)

    fun updateExerciseName(it: String) {
        _exercise.value.exerciseName = it
    }

    fun updateExerciseDescription(it: String) {
        _exercise.value.exerciseDescription = it
    }

}