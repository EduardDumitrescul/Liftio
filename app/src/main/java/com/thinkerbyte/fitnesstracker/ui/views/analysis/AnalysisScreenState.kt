package com.thinkerbyte.fitnesstracker.ui.views.analysis

import com.thinkerbyte.fitnesstracker.ui.views.analysis.overallCard.OverviewAnalysisCardState

data class AnalysisScreenState(
    val overviewAnalysisCardState: OverviewAnalysisCardState
) {
    companion object {
        fun default() = AnalysisScreenState(
            overviewAnalysisCardState = OverviewAnalysisCardState.default()
        )
    }
}