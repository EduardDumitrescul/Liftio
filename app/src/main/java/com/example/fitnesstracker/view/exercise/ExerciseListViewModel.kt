package com.example.fitnesstracker.view.exercise

import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.service.ExerciseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val exerciseService: ExerciseService
): ViewModel() {
    private var _exerciseSummaries: MutableStateFlow<List<ExerciseSummaryDTO>> = exerciseService.getExerciseSummaries()

    val exerciseSummaries = _exerciseSummaries.asStateFlow()



}
