package com.example.fitnesstracker.view.exercise

import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.repositories.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository
): ViewModel() {
    private var _exerciseSummaries: MutableStateFlow<List<ExerciseSummaryDTO>> = exerciseRepository.getExerciseSummaries()

    val exerciseSummaries = _exerciseSummaries.asStateFlow()



}
