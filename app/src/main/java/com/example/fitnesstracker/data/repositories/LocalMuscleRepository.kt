//package com.example.fitnesstracker.data.repositories
//
//import com.example.fitnesstracker.data.models.ExerciseMuscleCrossRef
//import com.example.fitnesstracker.data.models.Muscle
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.combine
//import kotlinx.coroutines.flow.filter
//import kotlinx.coroutines.flow.map
//
//class LocalMuscleRepository : MuscleRepository {
//    private var muscles = listOf(
//        Muscle(1, "Chest"),
//        Muscle(2, "Shoulders"),
//        Muscle(3, "Triceps"),
//        Muscle(4, "Biceps"),
//        Muscle(5, "Forearms"),
//        Muscle(6, "Glutes"),
//        Muscle(7, "Hamstring"),
//        Muscle(8, "Back"),
//        Muscle(9, "Traps"),
//    )
//
//    private var exerciseMuscleCrossRefs =
//        mutableListOf(
//        ExerciseMuscleCrossRef(1, 1, true),
//        ExerciseMuscleCrossRef(1, 2, false),
//        ExerciseMuscleCrossRef(1, 3, false),
//        ExerciseMuscleCrossRef(2, 4, true),
//        ExerciseMuscleCrossRef(2, 5, false),
//        ExerciseMuscleCrossRef(3, 6, true),
//        ExerciseMuscleCrossRef(3, 7, false),
//        ExerciseMuscleCrossRef(3, 8, false),
//        ExerciseMuscleCrossRef(3, 9, false),
//    )
//
//
//    override suspend fun getMuscleNames(): List<String> {
//        return muscles.map {muscle ->
//                muscle.name
//            }
//    }
//
//    override suspend fun getMuscleById(id: Int): Muscle? {
//        return muscles.find { muscle ->
//                muscle.id == id
//            }
//    }
//
//    override suspend fun getPrimaryMuscleByExerciseId(id: Int): Muscle? {
//        val ref = exerciseMuscleCrossRefs.find {
//                it.exerciseId == id && it.isPrimary
//            }
//
//        return muscles.find { it.id == ref?.muscleId }
//
//    }
//
//    override suspend fun getSecondaryMusclesByExerciseId(id: Int): List<Muscle> {
//        val refs = exerciseMuscleCrossRefs.filter {
//                it.exerciseId == id && !it.isPrimary
//            }
//
//        return refs.mapNotNull { r ->
//                muscles.find {
//                    it.id == r.muscleId
//                }
//            }
//
//    }
//
//    override suspend fun getMuscleId(name: String): Int {
//        return muscles.find {
//                it.name == name
//            }?.id ?: 0
//        }
//
//    override suspend fun addExerciseMuscleCrossRef(exerciseMuscleCrossRef: ExerciseMuscleCrossRef) {
//        TODO("Not yet implemented")
//    }
//}