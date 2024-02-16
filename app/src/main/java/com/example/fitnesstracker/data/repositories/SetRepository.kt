package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.daos.SetDao
import javax.inject.Inject

class SetRepository @Inject constructor(
    private val setDao: SetDao
) {
}