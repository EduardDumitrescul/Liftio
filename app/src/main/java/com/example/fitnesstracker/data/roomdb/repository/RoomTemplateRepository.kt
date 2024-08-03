package com.example.fitnesstracker.data.roomdb.repository

import com.example.fitnesstracker.data.dto.ExerciseDetailed
import com.example.fitnesstracker.data.models.Template
import com.example.fitnesstracker.data.repositories.TemplateRepository
import com.example.fitnesstracker.data.roomdb.dao.ExerciseDao
import com.example.fitnesstracker.data.roomdb.dao.MuscleDao
import com.example.fitnesstracker.data.roomdb.dao.SetDao
import com.example.fitnesstracker.data.roomdb.dao.TemplateDao
import com.example.fitnesstracker.data.roomdb.entity.TemplateExerciseCrossRef
import com.example.fitnesstracker.data.roomdb.entity.toEntity
import com.example.fitnesstracker.data.roomdb.entity.toModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "RoomTemplateRepository"

class RoomTemplateRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val muscleDao: MuscleDao,
    private val setDao: SetDao,
    private val templateDao: TemplateDao,
): TemplateRepository {
    override fun getBaseTemplates(): Flow<List<Template>> {
        val entities = templateDao.getBaseTemplates()
        return entities.map { list ->
            list.map {
                it.toModel()
            }
        }
    }

    override fun getTemplateById(templateId: Int): Flow<Template> {
        val templateEntity = templateDao.getTemplateById(templateId)
        return templateEntity.map {
            it?.toModel() ?: Template.default()
        }
    }

    override suspend fun addExerciseToTemplate(templateId: Int, exerciseId: Int) {
        val numberOfExercisesInTemplate = templateDao.getNumberOfExercisesInTemplate(templateId)
        val templateExercise = TemplateExerciseCrossRef(
            id = 0,
            templateId = templateId,
            exerciseId = exerciseId,
            index = numberOfExercisesInTemplate + 1
        )
        templateDao.insertTemplateExerciseCrossRef(templateExercise)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getExercisesWithSetsAndMuscles(templateId: Int): Flow<List<ExerciseDetailed>> {
        val templateExerciseFlow = templateDao.getTemplateExercisesByTemplateId(templateId)

        return templateExerciseFlow.flatMapLatest { templateExercises ->
            if (templateExercises.isEmpty()) {
                flowOf(emptyList())
            } else {
                val combinedFlows = templateExercises.map { templateExercise ->
                    val exerciseFlow = exerciseDao.getExerciseById(templateExercise.exerciseId)
                    val primaryMuscleFlow =
                        muscleDao.getPrimaryMuscleByExerciseId(templateExercise.exerciseId)
                    val secondaryMusclesFlow =
                        muscleDao.getSecondaryMusclesByExerciseId(templateExercise.exerciseId)
                    val setsFlow = setDao.getSetsFlowByTemplateExercise(templateExercise.id)

                    combine(
                        exerciseFlow,
                        primaryMuscleFlow,
                        secondaryMusclesFlow,
                        setsFlow
                    ) { exercise, primaryMuscle, secondaryMuscles, sets ->
                        ExerciseDetailed(
                            exercise = exercise!!.toModel(),
                            templateExerciseCrossRefId = templateExercise.id,
                            primaryMuscle = primaryMuscle!!.toModel(),
                            secondaryMuscles = secondaryMuscles.map { it.toModel() },
                            sets = sets.map { it.toModel() }
                        )
                    }
                }

                combine(combinedFlows) { it.toList() }
            }
        }
    }

    override suspend fun updateTemplateName(templateId: Int, templateName: String) {
        templateDao.updateTemplateName(templateId, templateName)
    }

    override suspend fun removeTemplateExerciseCrossRef(templateExerciseCrossRefId: Int) {
        templateDao.removeTemplateExerciseCrossRef(templateExerciseCrossRefId)
    }

    override suspend fun addTemplate(template: Template): Int {
        val entity = template.toEntity()
        return templateDao.insert(entity).toInt()
    }

    override suspend fun removeTemplate(templateId: Int) {
        templateDao.removeTemplate(templateId)
    }

}