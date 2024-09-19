package com.thinkerbyte.fitnesstracker.ui.views.workout.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkerbyte.fitnesstracker.data.datastore.SessionPreferences
import com.thinkerbyte.fitnesstracker.data.dto.DetailedWorkout
import com.thinkerbyte.fitnesstracker.services.SessionService
import com.thinkerbyte.fitnesstracker.services.WorkoutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplateDetailedViewModel @Inject constructor(
    private val templateId: Int,
    private val workoutService: WorkoutService,
    private val sessionService: SessionService
) : ViewModel() {

    private val _detailedWorkout = MutableStateFlow(DetailedWorkout.default())
    val detailedWorkout: StateFlow<DetailedWorkout> get() = _detailedWorkout

    private val _sessionPreferences = MutableStateFlow(SessionPreferences.default())
    val sessionPreferences: StateFlow<SessionPreferences> get() = _sessionPreferences.asStateFlow()

    private var collectionJob: Job? = null

    init {
        startCollectingTemplateData()
        fetchSessionPreferences()
    }

    private fun fetchSessionPreferences() {
        viewModelScope.launch {
            sessionService.getSessionPreferences().collect { preferences ->
                _sessionPreferences.update { preferences }
            }
        }
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