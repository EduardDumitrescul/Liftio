package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.daos.TemplateExerciseDao
import javax.inject.Inject

class TemplateExerciseRepository @Inject constructor(
    private val templateExerciseDao: TemplateExerciseDao
) {
}