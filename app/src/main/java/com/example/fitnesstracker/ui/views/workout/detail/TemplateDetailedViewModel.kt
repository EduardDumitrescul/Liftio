package com.example.fitnesstracker.ui.views.workout.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.DetailedWorkout
import com.example.fitnesstracker.services.WorkoutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplateDetailedViewModel @Inject constructor(
    private val templateId: Int,
    private val workoutService: WorkoutService
) : ViewModel() {

    private val _detailedWorkout = MutableStateFlow(DetailedWorkout.default())
    val detailedWorkout: StateFlow<DetailedWorkout> get() = _detailedWorkout

    private var collectionJob: Job? = null

    init {
        startCollectingTemplateData()
    }

    private fun startCollectingTemplateData() {
        collectionJob?.cancel() // Cancel any previous collection
        collectionJob = viewModelScope.launch {
            workoutService.getDetailedWorkout(templateId)
                .collect { templateDetailed ->
                    _detailedWorkout.value = templateDetailed
                }
        }
    }

    fun removeTemplate() {
        viewModelScope.launch(Dispatchers.IO) {
            workoutService.removeTemplate(templateId)
            stopCollectingTemplateData()
        }
    }

    private fun stopCollectingTemplateData() {
        collectionJob?.cancel()
    }

    suspend fun createWorkoutFromThisTemplate(): Int {
        val id = workoutService.createWorkoutFromTemplate(_detailedWorkout.value)
        return id
    }
}