package com.example.fitnesstracker.ui.components.exerciseCard

import com.example.fitnesstracker.data.dto.DetailedExercise
import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.data.models.Muscle
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.SetState
import com.example.fitnesstracker.ui.components.exerciseCard.setRow.toSetState

data class ExerciseCardState(
    val exercise: Exercise,
    val workoutExerciseCrossRefId: Int,
    val primaryMuscle: Muscle,
    val secondaryMuscles: List<Muscle>,
    val sets: List<SetState>,
    val progress: Progress
) {
    fun updateSet(set: SetState): ExerciseCardState {
        val updatedSets = sets.map {
            if(it.id == set.id) {
                set
            }
            else {
                it
            }
        }
        return this.copy(
            sets = updatedSets
        )
    }

    fun hasSets(): Boolean = sets.isNotEmpty()

    fun removeSet(id: Int): ExerciseCardState {
        val filteredSets = sets.filter {
            it.id != id
        }
        val indexedSets = filteredSets.mapIndexed { index, setState ->
            setState.copy(
                index = index + 1
            )
        }
        return this.copy(
            sets = indexedSets
        )
    }

    companion object {
        fun default(): ExerciseCardState {
            return ExerciseCardState(
                exercise = Exercise.default(),
                workoutExerciseCrossRefId = 0,
                primaryMuscle = Muscle.default(),
                secondaryMuscles = emptyList(),
                sets = emptyList(),
                progress = Progress.TODO
            )
        }
    }
}

fun DetailedExercise.toExerciseCardState(progress: Progress = Progress.TODO) =
    ExerciseCardState(
        exercise = exercise,
        workoutExerciseCrossRefId = templateExerciseCrossRefId,
        primaryMuscle = primaryMuscle,
        secondaryMuscles = secondaryMuscles,
        sets = sets.map {it.toSetState()},
        progress = progress
    )

fun ExerciseCardState.toDetailedExercise() =
    DetailedExercise(
        exercise = exercise,
        templateExerciseCrossRefId = workoutExerciseCrossRefId,
        primaryMuscle = primaryMuscle,
        secondaryMuscles = secondaryMuscles,
        sets = sets.map {it.toExerciseSet()}
    )
