package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.models.ExerciseSet

interface SetRepository {
    suspend fun insertSet(set: ExerciseSet)
}