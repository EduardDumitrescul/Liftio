package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.daos.ExerciseMuscleDao
import javax.inject.Inject

class ExerciseMuscleRepository  @Inject constructor (
    private val exerciseMuscleDao: ExerciseMuscleDao
)