package com.example.fitnesstracker.services

import com.example.fitnesstracker.data.dto.TemplateSummary
import com.example.fitnesstracker.data.models.Exercise
import com.example.fitnesstracker.data.models.Muscle
import com.example.fitnesstracker.data.models.Template
import com.example.fitnesstracker.data.repositories.ExerciseRepository
import com.example.fitnesstracker.data.repositories.MuscleRepository
import com.example.fitnesstracker.data.repositories.TemplateRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TemplateService @Inject constructor(
    private val templateRepository: TemplateRepository,
    private val muscleRepository: MuscleRepository,
    private val exerciseRepository: ExerciseRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTemplateSummaries(): Flow<List<TemplateSummary>> {
        val templates: Flow<List<Template>> = templateRepository.getBaseTemplates()

        val templateSummaries: Flow<List<TemplateSummary>> = templates.map { temps ->
            temps.map { temp ->
//                val muscles: Flow<List<Muscle>> = muscleRepository.getMusclesByTemplateId(temp.id)
//                val exercises: Flow<List<Exercise>> = exerciseRepository.getExercisesByTemplateId(temp.id)
//
//                combine(
//                    muscles,
//                    exercises
//                ) { m, ex ->
//                    TemplateSummary(
//                        temp.id,
//                        temp.name,
//                        m.map { it.name },
//                        ex.map { it.name }
//                    )
//                }

                TemplateSummary(
                    temp.id,
                    temp.name,
                    emptyList(),
                    emptyList()
                )
            }
//            combine(combinedFlows) {it.toList()}
        }

        return templateSummaries
    }

}