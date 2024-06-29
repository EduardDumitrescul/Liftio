package com.example.fitnesstracker.ui.views.exercise.edit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseEditViewModel @Inject constructor(
    val exerciseId: Int
): ViewModel() {
}