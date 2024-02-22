package com.example.fitnesstracker.data

import android.content.Context
import com.example.fitnesstracker.data.entities.ExerciseEntity
import com.example.fitnesstracker.data.entities.MuscleEntity
import com.example.fitnesstracker.utils.fromJson
import com.example.fitnesstracker.utils.readJsonFromAssets

class Seeder (
    private val context: Context,
    private val database: AppDatabase
) {
    private val musclesPath = "muscles.json"
    private val exercisesPath = "exercises.json"

    fun seed() {
        val muscles = loadMuscles()
        insertMuscles(muscles)
        val exercises = loadExercises()
        insertExercises(exercises)
    }

    private fun loadExercises(): Array<ExerciseEntity> {
        val jsonString = readJsonFromAssets(context, exercisesPath)
        val exercises: Array<ExerciseEntity> = fromJson(jsonString)
        return exercises
    }

    private fun insertExercises(exercises: Array<ExerciseEntity>) {
        val exerciseDao = database.exerciseDao()
        exercises.forEach {
            exerciseDao.insert(it)
        }
    }
    private fun loadMuscles(): Array<MuscleEntity> {
        val jsonString = readJsonFromAssets(context, musclesPath)
        val muscleList: Array<MuscleEntity> = fromJson(jsonString)
        return muscleList
    }

    private fun insertMuscles(muscleList: Array<MuscleEntity>) {
        val muscleDao = database.muscleDao()
        muscleList.forEach {
            muscleDao.insert(it)
        }
    }
}