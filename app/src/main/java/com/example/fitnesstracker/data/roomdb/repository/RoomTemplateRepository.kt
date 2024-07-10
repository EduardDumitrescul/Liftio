package com.example.fitnesstracker.data.roomdb.repository

import com.example.fitnesstracker.data.models.Template
import com.example.fitnesstracker.data.repositories.TemplateRepository
import com.example.fitnesstracker.data.roomdb.dao.TemplateDao
import com.example.fitnesstracker.data.roomdb.entity.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomTemplateRepository @Inject constructor(
    private val templateDao: TemplateDao
): TemplateRepository {
    override fun getBaseTemplates(): Flow<List<Template>> {
        val entities = templateDao.getBaseTemplates()
        return entities.map { list ->
            list.map {
                it.toModel()
            }
        }
    }

}