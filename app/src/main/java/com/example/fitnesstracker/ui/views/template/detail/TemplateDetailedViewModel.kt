package com.example.fitnesstracker.ui.views.template.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.TemplateDetailed
import com.example.fitnesstracker.services.TemplateService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplateDetailedViewModel @Inject constructor(
    val templateId: Int,
    private val templateService: TemplateService
) : ViewModel() {

    private val _templateDetailed = MutableStateFlow(TemplateDetailed.default())
    val templateDetailed: StateFlow<TemplateDetailed> get() = _templateDetailed

    private var collectionJob: Job? = null

    init {
        startCollectingTemplateData()
    }

    private fun startCollectingTemplateData() {
        collectionJob?.cancel() // Cancel any previous collection
        collectionJob = viewModelScope.launch {
            templateService.getTemplateWithExercisesById(templateId)
                .collect { templateDetailed ->
                    _templateDetailed.value = templateDetailed
                }
        }
    }

    fun removeTemplate() {
        viewModelScope.launch(Dispatchers.IO) {
            templateService.removeTemplate(templateId)
            stopCollectingTemplateData()
        }
    }

    private fun stopCollectingTemplateData() {
        collectionJob?.cancel()
    }
}