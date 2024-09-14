package com.example.fitnesstracker.ui.views.analysis

data class AnalysisScreenState(
    val overviewAnalysisCardState: OverviewAnalysisCardState
) {
    companion object {
        fun default() = AnalysisScreenState(
            overviewAnalysisCardState = OverviewAnalysisCardState.default()
        )
    }
}