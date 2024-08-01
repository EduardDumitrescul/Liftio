package com.example.fitnesstracker.data.repositories

import com.example.fitnesstracker.data.dto.ExerciseDetailed
import com.example.fitnesstracker.data.models.Template
import kotlinx.coroutines.flow.Flow

interface TemplateRepository {
    fun getBaseTemplates(): Flow<List<Template>>
    fun getTemplateById(templateId: Int): Flow<Template>
    suspend fun addExerciseToTemplate(templateId: Int, exerciseId: Int)
    fun getExercisesWithSetsAndMuscles(templateId: Int): Flow<List<ExerciseDetailed>>
    suspend fun updateTemplateName(templateId: Int, templateName: String)
    suspend fun removeTemplateExerciseCrossRef(templateExerciseCrossRefId: Int)
    suspend fun addTemplate(template: Template): Int
}