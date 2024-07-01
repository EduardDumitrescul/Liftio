package com.example.fitnesstracker.data

import android.content.Context
import com.example.fitnesstracker.data.entity.ExerciseEntity
import com.example.fitnesstracker.data.entity.ExerciseMuscleCrossRefEntity
import com.example.fitnesstracker.data.entity.MuscleEntity
import com.example.fitnesstracker.utils.fromJson
import com.example.fitnesstracker.utils.readJsonFromAssets

class Seeder (
    private val context: Context,
    private val database: AppDatabase
) {
    private val musclesPath = "muscles.json"
    private val exercisesPath = "exercises.json"
    private val exerciseMusclePath = "exerciseMuscles.json"

    fun seed() {
        val muscles = loadMuscles()
        insertMuscles(muscles)
        val exercises = loadExercises()
        insertExercises(exercises)
        val exerciseMuscles = loadExerciseMuscles()
        insertExerciseMuscles(exerciseMuscles)
    }

    private fun loadExercises(): Array<ExerciseEntity> {
        val jsonString = readJsonFromAssets(context, exercisesPath)
        return fromJson<Array<ExerciseEntity>>(jsonString)
    }

    private fun insertExercises(exercises: Array<ExerciseEntity>) {
        val exerciseDao = database.exerciseDao()
        exercises.forEach {
            exerciseDao.insert(it)
        }
    }

    private fun loadExerciseMuscles(): Array<ExerciseMuscleCrossRefEntity> {
        val jsonString = readJsonFromAssets(context, exerciseMusclePath)
        return fromJson<Array<ExerciseMuscleCrossRefEntity>>(jsonString)
    }

    private fun insertExerciseMuscles(exerciseMuscles: Array<ExerciseMuscleCrossRefEntity>) {
        val exerciseMuscleDao = database.exerciseMuscleDao()
        exerciseMuscles.forEach {
            exerciseMuscleDao.insert(it)
        }
    }
    private fun loadMuscles(): Array<MuscleEntity> {
        val jsonString = readJsonFromAssets(context, musclesPath)
        return fromJson<Array<MuscleEntity>>(jsonString)
    }

    private fun insertMuscles(muscleList: Array<MuscleEntity>) {
        val muscleDao = database.muscleDao()
        muscleList.forEach {
            muscleDao.insert(it)
        }
    }
}