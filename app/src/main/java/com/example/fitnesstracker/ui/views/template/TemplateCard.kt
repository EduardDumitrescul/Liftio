package com.example.fitnesstracker.ui.views.template

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.data.dto.TemplateSummary
import com.example.fitnesstracker.ui.components.chip.FilledChip
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TemplateCard(
    template: TemplateSummary
) {
    ElevatedCard(
        shape = AppTheme.shapes.roundedNormalCornerShape,
        colors = cardColors,
        elevation = cardElevation
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal)
        ) {
            Text(
                text = template.templateName,
                style = AppTheme.typography.headline
            )

            HorizontalDivider(color = AppTheme.colors.container)

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingNormal),
            ) {
                for(muscle in template.workedMuscles) {
                    FilledChip(label = muscle)
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingExtraSmall)
            ) {
                for(exercise in template.exerciseList) {
                    Text(
                        text = exercise,
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.onContainer.copy(alpha = 0.7f))
                }
            }
        }

    }
}

private val cardColors @Composable get() = CardColors(
    contentColor = AppTheme.colors.onContainer,
    containerColor = AppTheme.colors.container,
    disabledContentColor = AppTheme.colors.onContainer,
    disabledContainerColor = AppTheme.colors.container,
)

private val cardElevation @Composable get() = CardDefaults.elevatedCardElevation()

@Composable
@Preview(showBackground = true, backgroundColor = Color.GRAY.toLong(), widthDp = 300)
private fun PreviewWorkoutCard() {
    AppTheme {
        TemplateCard(
            template = TemplateSummary(
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
        )
    }
}