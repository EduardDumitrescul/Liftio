package com.thinkerbyte.fitnesstracker.ui.views.analysis.overallCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thinkerbyte.fitnesstracker.ui.components.card.LargeCard
import com.thinkerbyte.fitnesstracker.ui.components.charts.BarChart
import com.thinkerbyte.fitnesstracker.ui.components.charts.BarChartModel
import com.thinkerbyte.fitnesstracker.ui.components.charts.BarModel
import com.thinkerbyte.fitnesstracker.ui.components.chip.SingleChoiceChipGroup
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme

@Composable
fun OverviewCard(
    state: OverviewAnalysisCardState,
    modifier: Modifier = Modifier,
    onTimePeriodSelectionChanged: (String) -> Unit,
) {
    LargeCard(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        TitleRow()
        TimePeriodChipGroup(
            selected = state.selectedTimePeriod.label,
            onSelectionChanged = onTimePeriodSelectionChanged
        )
        StatsColumn(
            data = listOf(
                Pair("workouts completed", state.workoutCompleted.toString()),
                Pair("time trained", (state.timeTrainedInSeconds / 3600).toString() + " hours"),
                Pair("sets completed", state.setsCompleted.toString()),
            )
        )

        Chart(data = state.chartData)
    }
}

@Composable
private fun TitleRow() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Overview Analysis",
            style = AppTheme.typography.headline,
            color = AppTheme.colors.onContainer
        )

        Icon(
            Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            "view more",
            tint = AppTheme.colors.onContainer,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
private fun TimePeriodChipGroup(
    selected: String,
    onSelectionChanged: (String) -> Unit,
) {
    SingleChoiceChipGroup(
        options = OverviewAnalysisCardState.timePeriodOptions.map { it.label },
        selected = selected,
        onSelectionChanged = onSelectionChanged,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun StatsColumn(
    data: List<Pair <String, String>>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp)
    ) {
        data.forEach { pair ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "\u2022 " + pair.first,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.onContainer
                )
                Text(
                    text = pair.second,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.onContainer
                )
            }
        }
    }
}

@Composable
private fun Chart(
    data: List<Pair<String, Int>>
) {
    BarChart(
        model = BarChartModel(
            title = "# of workouts",
            titleStyle = AppTheme.typography.body,
            titleColor = AppTheme.colors.onContainer,
            barModels = data.map { BarModel(it.second, it.first) },
            backgroundColor = AppTheme.colors.container,
            barColor = AppTheme.colors.primary,
            helperLineColor = AppTheme.colors.outline,
            textStyle = AppTheme.typography.caption,
            textColor = AppTheme.colors.onContainer,
            leftAxisSteps = 5,
            leftAxisWidth = 24.dp,
        ),
        modifier = Modifier
            .height(160.dp)
            .padding(top = 16.dp)
            .padding(end = 16.dp)
    )
}

@Composable
@Preview
private fun PreviewOverviewAnalysisCard() {
    AppTheme {
        OverviewCard(
            state = OverviewAnalysisCardState.default(),
            onTimePeriodSelectionChanged = {},
        )
    }
}