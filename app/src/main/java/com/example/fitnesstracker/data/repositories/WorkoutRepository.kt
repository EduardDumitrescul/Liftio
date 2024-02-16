package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.daos.WorkoutDao
import javax.inject.Inject

class WorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao
) {
}