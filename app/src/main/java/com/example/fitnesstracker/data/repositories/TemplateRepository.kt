package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.daos.TemplateDao
import javax.inject.Inject

class TemplateRepository @Inject constructor(
    private val templateDao: TemplateDao
)