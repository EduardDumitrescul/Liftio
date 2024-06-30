package com.example.fitnesstracker.ui.views.template

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.data.dto.TemplateSummary
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.button.Fab
import com.example.fitnesstracker.ui.components.button.FilledButton
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun TemplateListView() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatelessTemplateListView(
    templates: List<TemplateSummary>
) {
    Scaffold(
        topBar = {
            LargeAppBar(
                title = "Templates",
                showNavigationIcon = false,
                actions = {},
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
                .padding(horizontal = AppTheme.dimensions.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingLarge)
        ) {
            item {
                FilledButton(
                    text = "start new workout",
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(templates) {template ->
                TemplateCard(template = template)
            }

        }


    }
}

@Preview
@Composable
fun PreviewTemplateListView() {
    val template = TemplateSummary(
        templateId = 0,
        templateName = "Push Workout",
        workedMuscles = listOf("chest", "shoulders", "triceps"),
        exerciseList = listOf(
            "3 x bench press",
            "4 x shoulder press",
            "3 x triceps pushdown",
            "2 x incline press",
            "5 x lateral raise")
    )
    AppTheme {
        StatelessTemplateListView(
            templates = listOf(template, template, template, template)
        )
    }
}