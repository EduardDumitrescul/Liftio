package com.example.fitnesstracker.ui.views.template.edit

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.button.TextButton
import com.example.fitnesstracker.ui.theme.AppTheme
import com.example.fitnesstracker.ui.views.template.detail.ExerciseCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateEditView(
    previouslySelectedExerciseId: Int = 0,
    viewModel: TemplateEditViewModel = hiltViewModel(),
    onNewExerciseButtonClick: () -> Unit
) {
    LaunchedEffect(previouslySelectedExerciseId) {
        if(previouslySelectedExerciseId > 0) {
            viewModel.addExercise(previouslySelectedExerciseId)
        }
    }

    val templateWithExercises by viewModel.templateDetailed.collectAsState()

    Scaffold(
        topBar = {
            LargeAppBar(title = "Edit - ${templateWithExercises.template.name}")
        },
        containerColor = AppTheme.colors.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = AppTheme.dimensions.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(templateWithExercises.exercisesWithSetsAndMuscles) { exerciseDetailed ->
                EditableExerciseCard(
                    exerciseDetailed = exerciseDetailed,
                    onClick = { /*TODO*/ },
                    updateSet = { set->
                        viewModel.updateSet(exerciseDetailed.exercise.id, set)
                    },
                    addSet = {
                        viewModel.addSet(exerciseDetailed.exercise.id)
                    },
                    removeSet = {
                        viewModel.removeSet(exerciseDetailed.exercise.id, it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                TextButton(
                    text = "new exercise",
                    imageVector = Icons.Rounded.Add,
                    onClick = onNewExerciseButtonClick)
            }
        }
    }
}

@Preview
@Composable
fun PreviewTemplateEditView() {
    AppTheme {
        TemplateEditView(
            onNewExerciseButtonClick = {}
        )
    }
}