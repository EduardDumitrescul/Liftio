package com.example.fitnesstracker.ui.views.workout.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import com.example.fitnesstracker.ui.components.button.TextButton

@Composable
fun AddExerciseButton(onNewExerciseButtonClick: () -> Unit) {
    TextButton(
        text = "new exercise",
        imageVector = Icons.Rounded.Add,
        onClick = onNewExerciseButtonClick
    )
}