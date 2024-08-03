package com.example.fitnesstracker.data.roomdb

import android.content.Context
import com.example.fitnesstracker.data.roomdb.entity.ExerciseEntity
import com.example.fitnesstracker.data.roomdb.entity.ExerciseMuscleCrossRefEntity
import com.example.fitnesstracker.data.roomdb.entity.MuscleEntity
import com.example.fitnesstracker.data.roomdb.entity.SetEntity
import com.example.fitnesstracker.data.roomdb.entity.WorkoutEntity
import com.example.fitnesstracker.data.roomdb.entity.WorkoutExerciseCrossRef
import com.example.fitnesstracker.utils.fromJson
import com.example.fitnesstracker.utils.readJsonFromAssets

private const val TAG = "Seeder"

class Seeder (
    private val context: Context,
    private val database: AppDatabase
) {
    private val musclesPath = "muscles.json"
    private val exercisesPath = "exercises.json"
    private val exerciseMusclePath = "exerciseMuscles.json"
    private val templatesPath = "templates.json"
    private val templateExerciseCrossRefPath = "templateExerciseCrossRef.json"
    private val setsPath = "sets.json"

    suspend fun seed() {
        val muscles = loadMuscles()
        insertMuscles(muscles)
        val exercises = loadExercises()
        insertExercises(exercises)
        val exerciseMuscles = loadExerciseMuscles()
        insertExerciseMuscles(exerciseMuscles)

        val templates = loadTemplates()
        insertTemplates(templates)

        insertTemplateExerciseCrossRefs()

        insertSets()
    }

    private suspend fun insertSets() {
        val jsonString = readJsonFromAssets(context, setsPath)
        val entities = fromJson<List<SetEntity>>(jsonString)
        entities.forEach {
            database.setDao().insertSet(it)
        }
    }

    private suspend fun insertTemplateExerciseCrossRefs() {
        val jsonString = readJsonFromAssets(context, templateExerciseCrossRefPath)
        val entities = fromJson<List<WorkoutExerciseCrossRef>>(jsonString)
        entities.forEach {
            database.workoutDao().insertTemplateExerciseCrossRef(it)
        }
    }

    private fun loadTemplates(): List<WorkoutEntity> {
        val jsonString = readJsonFromAssets(context, templatesPath)
        return fromJson<List<WorkoutEntity>>(jsonString)
    }

    private suspend fun insertTemplates(templates: List<WorkoutEntity>) {
        val templateDao = database.workoutDao()
        templates.forEach {
            templateDao.insert(it)
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