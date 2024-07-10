package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnesstracker.data.roomdb.entity.TemplateEntity
import com.example.fitnesstracker.data.roomdb.entity.TemplateExerciseCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDao {
    @Query("select * from templates " +
            "where isBaseTemplate is 1")
    fun getBaseTemplates(): Flow<List<TemplateEntity>>

    @Insert
    fun insert(it: TemplateEntity)

    @Insert
    fun insertTemplateExerciseCrossRef(it: TemplateExerciseCrossRef)
}