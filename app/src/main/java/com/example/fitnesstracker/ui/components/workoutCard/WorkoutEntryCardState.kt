package com.example.fitnesstracker.ui.components.workoutCard

import com.example.fitnesstracker.data.dto.ExerciseWithSets
import com.example.fitnesstracker.data.dto.WorkoutEntry
import com.example.fitnesstracker.data.models.ExerciseSet
import java.time.format.DateTimeFormatter
import java.util.Locale

data class WorkoutEntryCardState(
    val id: Int,
    val name: String,
    val date: String,
    val duration: String,
    val weightMoved: String,
    val exerciseSummaries: List<ExerciseEntrySummary>
) {
    companion object {
        fun default() = WorkoutEntryCardState(
            id = 0,
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

private fun ExerciseWithSets.asExerciseEntrySummary() = ExerciseEntrySummary(
    name = "${sets.size} x ${exercise.name}",
    effort = getBestEffort(sets)
)

private fun getBestEffort(sets: List<ExerciseSet>): String {
    if(sets.isEmpty()) {
        return ""
    }
    var bestSet = sets.first()
    for(set in sets) {
        if(set.weight > bestSet.weight) {
            bestSet = set
        }
        else if(set.weight == bestSet.weight && set.reps > bestSet.reps) {
            bestSet = set
        }
    }
    return if(bestSet.weight == 0) {
        "${bestSet.reps} reps"
    }
    else {
        "${bestSet.reps} x ${bestSet.weight}kg"
    }

}
private fun computeTotalWeight(exercisesWithSets: List<ExerciseWithSets>): String {
    var weight: Long = 0
    for(exerciseWithSets in exercisesWithSets) {
        weight += exerciseWithSets.getTotalWeightMoved()
    }
    return "$weight kg"
}

fun WorkoutEntry.asWorkoutEntryCardState() = WorkoutEntryCardState(
    id = id,
    name = name,
    date = timeStarted.format(DateTimeFormatter.ofPattern("dd MMMM", Locale.ENGLISH)),
    duration = formatDuration(duration),
    weightMoved = computeTotalWeight(exercisesWithSets),
    exerciseSummaries = exercisesWithSets.map { it.asExerciseEntrySummary() }
)

fun formatDuration(value: Long): String {
    val hours = value / 3600
    val minutes = value / 60 % 60

    var string = ""
    if(hours > 0)
        string += "${hours}h "

    string += "${minutes}m"

    return string
}