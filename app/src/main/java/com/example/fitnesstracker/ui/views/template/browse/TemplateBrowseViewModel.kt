package com.example.fitnesstracker.ui.views.template.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.WorkoutSummary
import com.example.fitnesstracker.services.WorkoutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TemplateBrowseViewModel @Inject constructor(
    private val workoutService: WorkoutService
) :ViewModel()
{

    val templateSummaries: StateFlow<List<WorkoutSummary>> = workoutService
        .getTemplateSummaries()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    suspend fun createBlankWorkout(): Int {
        return workoutService.createBlankWorkout()
    }
}