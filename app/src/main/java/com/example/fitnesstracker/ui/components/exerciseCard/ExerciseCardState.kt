package com.example.fitnesstracker.ui.components.exerciseCard

import android.content.res.Resources.NotFoundException
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

    fun keepOnlyPerformedSets(): ExerciseCardState {
        return this.copy(
            sets = sets.filter {
                it.status == Progress.DONE
            }
        )
    }

    fun hasSets(): Boolean = sets.isNotEmpty()

    fun findSet(id: Int): SetState {
       val set = sets.find {
            it.id == id
        }
        if(set == null) {
            throw NotFoundException("Set with id $id does not exist")
        }
        return set
    }

    fun addSet(): ExerciseCardState {
        val set =
            if(sets.isNotEmpty())
                sets.last().copy(index = sets.last().index + 1)
            else
                SetState(0, workoutExerciseCrossRefId, 1, 12, 20, Progress.TODO)

        return this.copy(
            sets = sets.plus(set)
        )
    }

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

fun DetailedExercise.toExerciseCardState() =
    ExerciseCardState(
        exercise = exercise,
        workoutExerciseCrossRefId = templateExerciseCrossRefId,
        primaryMuscle = primaryMuscle,
        secondaryMuscles = secondaryMuscles,
        sets = sets.map {it.toSetState()},
        progress = Progress.TODO
    )

fun ExerciseCardState.toDetailedExercise() =
    DetailedExercise(
        exercise = exercise,
        templateExerciseCrossRefId = workoutExerciseCrossRefId,
        primaryMuscle = primaryMuscle,
        secondaryMuscles = secondaryMuscles,
        sets = sets.map {it.toExerciseSet()}
    )
