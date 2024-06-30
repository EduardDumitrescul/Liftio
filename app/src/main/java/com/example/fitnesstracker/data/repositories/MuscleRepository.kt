package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.models.ExerciseMuscleCrossRef
import com.example.fitnesstracker.data.models.Muscle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class MuscleRepository {
    private var muscles = MutableStateFlow(listOf(
        Muscle(1, "Chest"),
        Muscle(2, "Shoulders"),
        Muscle(3, "Triceps"),
        Muscle(4, "Biceps"),
        Muscle(5, "Forearms"),
        Muscle(6, "Glutes"),
        Muscle(7, "Hamstring"),
        Muscle(8, "Back"),
        Muscle(9, "Traps"),
    ))

    private var exerciseMuscleCrossRefs = MutableStateFlow(
        mutableListOf(
        ExerciseMuscleCrossRef(1, 1, true),
        ExerciseMuscleCrossRef(1, 2, false),
        ExerciseMuscleCrossRef(1, 3, false),
        ExerciseMuscleCrossRef(2, 4, true),
        ExerciseMuscleCrossRef(2, 5, false),
        ExerciseMuscleCrossRef(3, 6, true),
        ExerciseMuscleCrossRef(3, 7, false),
        ExerciseMuscleCrossRef(3, 8, false),
        ExerciseMuscleCrossRef(3, 9, false),
    )
    )

    fun getMuscleNames(): Flow<List<String>> {
        return muscles.map { list->
            list.map {muscle ->
                muscle.name
            }
        }
    }

    fun getMuscleById(id: Int): Flow<Muscle?> {
        return muscles.map { list ->
            list.find { muscle ->
                muscle.id == id
            }
        }
    }

    fun getPrimaryMuscleByExerciseId(id: Int): Flow<Muscle?> {
        val ref = exerciseMuscleCrossRefs.map { list ->
            list.find {
                it.exerciseId == id && it.isPrimary
            }
        }

        return muscles.combine(ref) { muscleList, r ->
            muscleList.find { it.id == r?.muscleId }
        }
    }

    fun getSecondaryMusclesByExerciseId(id: Int): Flow<List<Muscle>> {
        val refs = exerciseMuscleCrossRefs.map { list ->
            list.filter {
                it.exerciseId == id && !it.isPrimary
            }
        }

        return muscles.combine(refs) { muscleList, refs ->
            refs.mapNotNull { r ->
                muscleList.find {
                    it.id == r.muscleId
                }
            }
        }
    }

    fun getMuscleId(name: String): Int {
        return muscles.value.find {
            it.name == name
        }?.id ?: 0
    }

    fun addExerciseMuscleCrossRef(exerciseMuscleCrossRef: ExerciseMuscleCrossRef) {
        exerciseMuscleCrossRefs.value.add(exerciseMuscleCrossRef)
    }
}