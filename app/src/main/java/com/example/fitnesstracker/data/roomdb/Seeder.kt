package com.example.fitnesstracker.data.roomdb

import android.content.Context
import com.example.fitnesstracker.data.roomdb.entity.ExerciseEntity
import com.example.fitnesstracker.data.roomdb.entity.ExerciseMuscleCrossRefEntity
import com.example.fitnesstracker.data.roomdb.entity.MuscleEntity
import com.example.fitnesstracker.data.roomdb.entity.SetEntity
import com.example.fitnesstracker.data.roomdb.entity.WorkoutEntity
import com.example.fitnesstracker.data.roomdb.entity.WorkoutExerciseCrossRefEntity
import com.example.fitnesstracker.utils.fromJson
import com.example.fitnesstracker.utils.readJsonFromAssets
import java.time.LocalDateTime

class Seeder (
    private val context: Context,
    private val database: AppDatabase
) {
    private val musclesPath = "muscles.json"
    private val exercisesPath = "exercises.json"
    private val exerciseMusclePath = "exerciseMuscles.json"
    private val workoutsPath = "workouts.json"
    private val workoutExerciseCrossRefPath = "workoutExerciseCrossRef.json"
    private val setsPath = "sets.json"

    suspend fun seed() {
        val muscles = loadMuscles()
        insertMuscles(muscles)
        val exercises = loadExercises()
        insertExercises(exercises)
        val exerciseMuscles = loadExerciseMuscles()
        insertExerciseMuscles(exerciseMuscles)

        val workouts = loadWorkouts()
        insertWorkouts(workouts)

        insertWorkoutExerciseCrossRefs()

        insertSets()
    }

    private suspend fun insertSets() {
        val jsonString = readJsonFromAssets(context, setsPath)
        val entities = fromJson<List<SetEntity>>(jsonString)
        entities.forEach {
            database.setDao().insertSet(it)
        }
    }

    private suspend fun insertWorkoutExerciseCrossRefs() {
        val jsonString = readJsonFromAssets(context, workoutExerciseCrossRefPath)
        val entities = fromJson<List<WorkoutExerciseCrossRefEntity>>(jsonString)
        entities.forEach {
            database.workoutDao().insertWorkoutExerciseCrossRef(it)
        }
    }

    private fun loadWorkouts(): List<WorkoutEntity> {
        val jsonString = readJsonFromAssets(context, workoutsPath)
        return fromJson<List<WorkoutEntity>>(jsonString)
    }

    private suspend fun insertWorkouts(workouts: List<WorkoutEntity>) {
        val workoutDao = database.workoutDao()
        workouts.forEach {
            val workout = it.copy(
                timeStarted = LocalDateTime.MIN,
                duration = 0
            )
            workoutDao.insert(workout)
        }
    }

    private fun loadExercises(): Array<ExerciseEntity> {
        val jsonString = readJsonFromAssets(context, exercisesPath)
        return fromJson<Array<ExerciseEntity>>(jsonString)
    }

    private suspend fun insertExercises(exercises: Array<ExerciseEntity>) {
        val exerciseDao = database.exerciseDao()
        exercises.forEach {
            exerciseDao.insert(it)
        }
    }

    private fun loadExerciseMuscles(): Array<ExerciseMuscleCrossRefEntity> {
        val jsonString = readJsonFromAssets(context, exerciseMusclePath)
        return fromJson<Array<ExerciseMuscleCrossRefEntity>>(jsonString)
    }

    private suspend fun insertExerciseMuscles(exerciseMuscles: Array<ExerciseMuscleCrossRefEntity>) {
        val muscleDao = database.muscleDao()
        exerciseMuscles.forEach {
            muscleDao.insert(it)
        }
    }
    private fun loadMuscles(): Array<MuscleEntity> {
        val jsonString = readJsonFromAssets(context, musclesPath)
        return fromJson<Array<MuscleEntity>>(jsonString)
    }

    private suspend fun insertMuscles(muscleList: Array<MuscleEntity>) {
        val muscleDao = database.muscleDao()
        muscleList.forEach {
            muscleDao.insert(it)
        }
    }
}