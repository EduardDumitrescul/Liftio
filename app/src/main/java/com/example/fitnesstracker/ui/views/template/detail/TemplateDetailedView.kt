package com.example.fitnesstracker.ui.views.template.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun TemplateDetailedView(
    viewModel: TemplateDetailedViewModel = hiltViewModel()
) {
    Text(text = "Template Detailed View ${viewModel.templateId}")
}

@Preview
@Composable
fun PreviewTemplateDetailView() {
    AppTheme {
        TemplateDetailedView()
    }
}