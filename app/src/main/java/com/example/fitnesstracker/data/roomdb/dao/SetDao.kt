package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fitnesstracker.data.roomdb.entity.SetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SetDao {

    @Query("select s.* " +
            "from sets s " +
            "where s.workoutExerciseId = :templateExerciseCrossRefId"
    )
    fun getSetsFlowByTemplateExercise(templateExerciseCrossRefId: Int): Flow<List<SetEntity>>

    @Query("select s.* " +
            "from sets s " +
            "join workoutExerciseCrossRefs te on te.id = :templateExerciseCrossRefId " +
            "where te.id = s.workoutExerciseId"
    )
    suspend fun getSetsByTemplateExercise(templateExerciseCrossRefId: Int): List<SetEntity>

    @Insert
    suspend fun insertSet(it: SetEntity)

    @Query("select * from sets where id=:id")
    suspend fun getSet(id: Int): SetEntity

    @Query("delete from sets where id=:id")
    suspend fun removeSet(id: Int)

    @Query("update sets " +
            "set `index` = `index` - 1 " +
            "where workoutExerciseId = :templateExerciseCrossRefId " +
            "and `index`>:indexToBeRemoved")
    suspend fun updateIndexes(templateExerciseCrossRefId: Int, indexToBeRemoved: Int)

    @Update
    suspend fun updateSet(set: SetEntity)

}