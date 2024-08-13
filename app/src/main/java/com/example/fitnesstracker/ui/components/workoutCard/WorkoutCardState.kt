package com.example.fitnesstracker.ui.components.workoutCard

data class WorkoutCardState(
    val name: String,
    val date: String,
    val duration: String,
    val weightMoved: String,
    val exerciseSummaries: List<ExerciseEntrySummary>
) {
    companion object {
        fun default() = WorkoutCardState(
            name = "Workout",
            date = "14 july",
            duration = "1h 15m",
            weightMoved = "1,234 kg",
            exerciseSummaries = listOf(
                ExerciseEntrySummary.default(),
                ExerciseEntrySummary.default(),
                ExerciseEntrySummary.default(),
                ExerciseEntrySummary.default(),
            )
        )
    }
}

data class ExerciseEntrySummary (
    val name: String,
    val effort: String
) {
    companion object {
        fun default() = ExerciseEntrySummary(
            name = "2 x push up",
            effort = "50 reps"
        )
    }
}