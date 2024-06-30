package com.example.fitnesstracker.data.dto

data class TemplateSummary(
    val templateId: Int,
    val templateName: String,
    val workedMuscles: List<String>,
    val exerciseList: List<String>
)