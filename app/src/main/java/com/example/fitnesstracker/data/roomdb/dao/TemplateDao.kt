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
    suspend fun insertTemplateExerciseCrossRef(it: TemplateExerciseCrossRef)

    @Query("select t.* " +
            "from templates t " +
            "where t.id = :templateId")
    fun getTemplateById(templateId: Int): Flow<TemplateEntity>

    @Query("select count(*) " +
         "from templateExerciseCrossRefs te " +
         "where te.templateId = :templateId")
    suspend fun getNumberOfExercisesInTemplate(templateId: Int): Int

    @Query("select te.* " +
            "from templateExerciseCrossRefs te " +
            "where te.templateId = :templateId " +
            "order by te.`index`")
    fun getTemplateExercisesByTemplateId(templateId: Int): Flow<List<TemplateExerciseCrossRef>>

    @Query("update templates " +
            "set name = :templateName " +
            "where id = :templateId")
    suspend fun updateTemplateName(templateId: Int, templateName: String)

    @Query("delete from templateExerciseCrossRefs " +
            "where id = :templateExerciseCrossRefId")
    suspend fun removeTemplateExerciseCrossRef(templateExerciseCrossRefId: Int)
}