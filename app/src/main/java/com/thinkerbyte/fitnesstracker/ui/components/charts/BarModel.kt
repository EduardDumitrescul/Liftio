package com.thinkerbyte.fitnesstracker.ui.components.charts

data class BarModel(
    val value: Int,
    val label: String,
) {
    companion object {
        fun default() = BarModel(478, "bar")
    }
}
