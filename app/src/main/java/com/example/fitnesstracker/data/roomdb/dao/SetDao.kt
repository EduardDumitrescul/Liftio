package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnesstracker.data.roomdb.entity.SetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SetDao {

    @Query("select s.* " +
            "from sets s " +
            "join templateExerciseCrossRefs te on te.id = :exerciseId and te.templateId = :templateId " +
            "where te.id = s.templateExerciseId")
    fun getSetsByTemplateExercise(templateId: Int, exerciseId: Int): Flow<List<SetEntity>>

    @Insert
    fun insertSet(it: SetEntity)

    @Query("select * from sets where id=:id")
    suspend fun getSet(id: Int): SetEntity

    @Query("delete from sets where id=:id")
    suspend fun removeSet(id: Int)

    @Query("update sets " +
            "set `index` = `index` - 1 " +
            "where templateExerciseId = :templateExerciseCrossRefId " +
            "and `index`>:indexToBeRemoved")
    suspend fun updateIndexes(templateExerciseCrossRefId: Int, indexToBeRemoved: Int)

}