package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.daos.MuscleDao
import javax.inject.Inject


class MuscleRepository @Inject constructor (
    private val muscleDao: MuscleDao
){
}