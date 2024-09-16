package com.example.fitnesstracker.ui.views.workout.browse

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.WorkoutSummary
import com.example.fitnesstracker.data.models.Workout
import com.example.fitnesstracker.services.WorkoutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

private const val TAG = "TemplateBrowseViewModel"

@HiltViewModel
class TemplateBrowseViewModel @Inject constructor(
    private val workoutService: WorkoutService,
) :ViewModel()
{
    val templateSummaries: StateFlow<List<WorkoutSummary>> = workoutService
        .getTemplateSummaries()
        .stateIn(
            viewModelScope,
            WhileSubscribed(5000),
            emptyList()
        )

    private val _ongoingWorkoutState: MutableStateFlow<OngoingWorkoutState> = MutableStateFlow(OngoingWorkoutState.default())
    val ongoingWorkoutState: StateFlow<OngoingWorkoutState> get() = _ongoingWorkoutState.asStateFlow()

    init {
        fetchOngoingWorkoutState()
        runUpdateTicker()
    }

    private fun runUpdateTicker() {
        val ticker = flow {
            while (true) {
                emit(System.currentTimeMillis()) // You can use this as a trigger
                delay(1000L) // Emit every second
            }
        }
        viewModelScope.launch {
            ticker.collect {
                _ongoingWorkoutState.update {
                    _ongoingWorkoutState.value.copy(
                        durationInSeconds = _ongoingWorkoutState.value.durationInSeconds + 1
                    )
                }
            }
        }
    }

    private fun fetchOngoingWorkoutState() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "fetchOngoingWorkoutState()")
                val workoutFlow: Flow<Workout?> = workoutService.getOngoingWorkout()
                workoutFlow.collect { workout ->
                    Log.d(TAG, workout.toString())
                    _ongoingWorkoutState.update {
                        if(workout == null) {
                            Log.d(TAG, "null")
                            OngoingWorkoutState.default()
                        }
                        else {
                            OngoingWorkoutState(
                                id = workout.id,
                                exists = true,
                                name = workout.name,
                                durationInSeconds = Duration.between(
                                    workout.timeStarted,
                                    LocalDateTime.now()
                                ).seconds
                            )
                        }
                    }
                }
            }
            catch (exception: Exception) {
                Log.d(TAG, "exception")
                _ongoingWorkoutState.value = OngoingWorkoutState.default()
            }
        }
    }

    suspend fun createBlankWorkout(): Int {
        return workoutService.createBlankWorkout()
    }
}