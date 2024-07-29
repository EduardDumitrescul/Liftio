package com.example.fitnesstracker.services

import android.util.Log
import com.example.fitnesstracker.data.dto.TemplateDetailed
import com.example.fitnesstracker.data.dto.TemplateSummary
import com.example.fitnesstracker.data.models.ExerciseSet
import com.example.fitnesstracker.data.repositories.ExerciseRepository
import com.example.fitnesstracker.data.repositories.MuscleRepository
import com.example.fitnesstracker.data.repositories.SetRepository
import com.example.fitnesstracker.data.repositories.TemplateRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

private const val TAG = "TemplateService"

class TemplateService @Inject constructor(
    private val templateRepository: TemplateRepository,
    private val setRepository: SetRepository,
    private val muscleRepository: MuscleRepository,
    private val exerciseRepository: ExerciseRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTemplateSummaries(): Flow<List<TemplateSummary>> {
        return templateRepository.getBaseTemplates().flatMapLatest { templates ->
            if (templates.isEmpty()) {
                Log.d(TAG, "No templates found.")
                flowOf(emptyList())
            } else {
                val templateSummaryFlows = templates.map { template ->
                    val musclesFlow = muscleRepository.getMusclesByTemplateId(template.id)
                    val exercisesFlow = exerciseRepository.getExercisesWithSetsByTemplateId(template.id)

                    combine(musclesFlow, exercisesFlow) { muscles, exercises ->
                        Log.d(TAG, "Muscles: $muscles")
                        Log.d(TAG, "Exercises: $exercises")

                        TemplateSummary(
                            templateId = template.id,
                            templateName = template.name,
                            workedMuscles = muscles.map { it.name },
                            exerciseList = exercises.map { "${it.sets.size} x ${it.exercise.name}" }
                        )
                    }
                }

                Log.d(TAG, templateSummaryFlows.toString())

                combine(templateSummaryFlows) {
                    val summaries = it.toList()
                    Log.d(TAG, "Combined summaries: $summaries")
                    summaries
                }
            }
        }
    }

    fun getTemplateWithExercisesById(templateId: Int): Flow<TemplateDetailed> {
        val templateFlow = templateRepository.getTemplateById(templateId)
        val exercises = templateRepository.getExercisesWithSetsAndMuscles(templateId)

        return combine(
            templateFlow,
            exercises
        ) { temp, ex ->
            TemplateDetailed(
                template = temp,
                exercisesWithSetsAndMuscles = ex
            )
        }
    }

    suspend fun addExerciseToTemplate(templateId: Int, exerciseId: Int) {
        templateRepository.addExerciseToTemplate(templateId, exerciseId)
    }

    suspend fun updateTemplateName(templateId: Int, templateName: String) {
        templateRepository.updateTemplateName(templateId, templateName)
    }

    suspend fun removeExerciseFromTemplate(templateExerciseCrossRefId: Int) {
        templateRepository.removeTemplateExerciseCrossRef(templateExerciseCrossRefId)
    }

    suspend fun removeSetFromTemplateExercise(templateExerciseCrossRefId: Int, setId: Int) {
        val set = setRepository.getSet(setId)
        setRepository.updateSetIndexes(templateExerciseCrossRefId, set.index)
        setRepository.removeSet(setId)
    }

    suspend fun addSetToTemplateExercise(templateExerciseCrossRefId: Int) {
        val sets: List<ExerciseSet> = setRepository.getSetsForTemplateExercise(templateExerciseCrossRefId)

        val newSet =
            if(sets.isEmpty())
                ExerciseSet(0, templateExerciseCrossRefId, 1, 12, 20)
            else
                sets.last().copy(
                    id = 0,
                    index = sets.last().index + 1
                )


        setRepository.addSet(newSet)
    }
}