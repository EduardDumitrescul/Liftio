package com.example.fitnesstracker.ui.views.template.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.button.FilledButton
import com.example.fitnesstracker.ui.components.exerciseCard.ExerciseCard
import com.example.fitnesstracker.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateDetailedView(
    navigateToTemplateEditView: (Int) -> Unit,
    navigateToOngoingWorkout: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: TemplateDetailedViewModel = hiltViewModel(),
) {
    val templateWithExercises by viewModel.detailedWorkout.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            LargeAppBar(
                title = templateWithExercises.name,
                actions = {
                    IconButton(onClick = {
                        navigateBack()
                        viewModel.removeTemplate()
                    }) {
                        Icon(Icons.Rounded.Delete, "remove template")
                    }
                    IconButton(onClick ={
                        navigateToTemplateEditView(templateWithExercises.id)
                        }
                    ) {
                        Icon(Icons.Rounded.Edit, "edit template")
                    }
                },
                onNavigationIconClick = navigateBack
            )
        },
        containerColor = AppTheme.colors.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = AppTheme.dimensions.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                FilledButton(
                    text = "start training",
                    onClick = {
                        coroutineScope.launch {
                            val id  = viewModel.createWorkoutFromThisTemplate()
                            navigateToOngoingWorkout(id)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            items(templateWithExercises.exercisesWithSetsAndMuscles) {
                ExerciseCard(
                    detailedExercise = it,
                    onClick = {},
                    modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Preview
@Composable
fun PreviewTemplateDetailView() {
    AppTheme {
        TemplateDetailedView({}, {}, {})
    }
}