package com.thinkerbyte.fitnesstracker.data.dto

import com.thinkerbyte.fitnesstracker.data.models.Exercise
import com.thinkerbyte.fitnesstracker.data.models.ExerciseSet

data class ExerciseWithSets (
    val exercise: Exercise,
    val sets: List<ExerciseSet>
) {
    fun getTotalWeightMoved(): Int {
        var weight = 0
        for(set in sets) {
            weight += set.getTotalWeightMoved()
        }
        return weight
    }
}
