package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.models.Template
import kotlinx.coroutines.flow.Flow

interface TemplateRepository {
    fun getBaseTemplates(): Flow<List<Template>>

}