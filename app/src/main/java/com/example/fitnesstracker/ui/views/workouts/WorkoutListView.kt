package com.example.fitnesstracker.ui.views.workouts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.button.Fab
import com.example.fitnesstracker.ui.components.button.FilledButton
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutListView() {
    Scaffold(
        topBar = {
            LargeAppBar(
                title = "Workouts",
                showNavigationIcon = false,
                showMoreActionsIcon = false,
            )
        },
        floatingActionButton = {
            Fab(Icons.Rounded.Add, "more options", onClick = { /*TODO*/ })
        },
        containerColor = AppTheme.colors.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                FilledButton(
                    text = "start new workout",
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }


    }
}

@Preview
@Composable
fun PreviewWorkoutListView() {
    AppTheme {
        WorkoutListView()
    }
}