package com.example.fitnesstracker.data.dto

data class WorkoutSummary(
    val id: Int,
    val name: String,
    val workedMuscles: List<String>,
    val exerciseList: List<String>
)