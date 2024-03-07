package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.daos.ExerciseDao
import javax.inject.Inject

class ExerciseRepository @Inject constructor (
    private val exerciseDao: ExerciseDao
)