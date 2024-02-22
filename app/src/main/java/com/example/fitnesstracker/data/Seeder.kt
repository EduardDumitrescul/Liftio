package com.example.fitnesstracker.data

import android.content.Context
import com.example.fitnesstracker.data.entities.MuscleEntity
import com.example.fitnesstracker.utils.fromJson
import com.example.fitnesstracker.utils.readJsonFromAssets

class Seeder (
    private val context: Context,
    private val database: AppDatabase
) {
    private val musclesPath = "muscles.json"

    fun seed() {
        val muscles = loadMuscles()
        insertMuscles(muscles)
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