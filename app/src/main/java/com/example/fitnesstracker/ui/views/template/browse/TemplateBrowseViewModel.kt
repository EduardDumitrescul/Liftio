package com.example.fitnesstracker.ui.views.template.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.dto.TemplateSummary
import com.example.fitnesstracker.services.TemplateService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TemplateBrowseViewModel @Inject constructor(
    templateService: TemplateService
) :ViewModel()
{
    val templateSummaries: StateFlow<List<TemplateSummary>> = templateService
        .getTemplateSummaries()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}