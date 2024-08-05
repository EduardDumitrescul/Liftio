package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnesstracker.data.roomdb.entity.WorkoutEntity
import com.example.fitnesstracker.data.roomdb.entity.WorkoutExerciseCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("select * from workouts " +
            "where isTemplate is 1"
    )
    fun getTemplates(): Flow<List<WorkoutEntity>>

    @Insert
    suspend fun insert(it: WorkoutEntity): Long

    @Insert
    suspend fun insertWorkoutExerciseCrossRef(it: WorkoutExerciseCrossRef): Long

    @Query("select w.* " +
            "from workouts w " +
            "where w.id = :templateId")
    fun getTemplateById(templateId: Int): Flow<WorkoutEntity?>

    @Query("select count(*) " +
         "from workoutExerciseCrossRefs te " +
         "where te.workoutId = :templateId")
    suspend fun getNumberOfExercisesInTemplate(templateId: Int): Int

    @Query("select te.* " +
            "from workoutExerciseCrossRefs te " +
            "where te.workoutId = :templateId " +
            "order by te.`index`")
    fun getTemplateExercisesByTemplateId(templateId: Int): Flow<List<WorkoutExerciseCrossRef>>

    @Query("update workouts " +
            "set name = :templateName " +
            "where id = :templateId")
    suspend fun updateTemplateName(templateId: Int, templateName: String)

    @Query("delete from workoutExerciseCrossRefs " +
            "where id = :templateExerciseCrossRefId")
    suspend fun removeTemplateExerciseCrossRef(templateExerciseCrossRefId: Int)

    @Query("delete from workouts where id =:templateId")
    suspend fun removeTemplate(templateId: Int)
}