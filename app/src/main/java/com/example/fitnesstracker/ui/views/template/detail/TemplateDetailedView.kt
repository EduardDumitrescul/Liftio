package com.example.fitnesstracker.ui.views.template.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateDetailedView(
    viewModel: TemplateDetailedViewModel = hiltViewModel()
) {
    val templateWithExercises by viewModel.templateWithExercises.collectAsState()

    Scaffold(
        topBar = {
            LargeAppBar(title = templateWithExercises.template.name)
        },
        containerColor = AppTheme.colors.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {

        }
    }
}

@Preview
@Composable
fun PreviewTemplateDetailView() {
    AppTheme {
        TemplateDetailedView()
    }
}