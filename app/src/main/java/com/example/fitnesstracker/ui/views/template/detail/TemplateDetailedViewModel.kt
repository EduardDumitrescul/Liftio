package com.example.fitnesstracker.ui.views.template.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TemplateDetailedViewModel @Inject constructor(
    val templateId: Int
): ViewModel() {

}