package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.fitnesstracker.data.dto.ExerciseWithMuscles
import com.example.fitnesstracker.data.roomdb.entity.ExerciseEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat

@Dao
interface ExerciseDao {
    @Insert
    fun insert(it: ExerciseEntity)

    @Query("Select * from exercises")
    fun getExercises(): Flow<List<ExerciseEntity>>

    @Query("Select * from exercises " +
            "where id=:exerciseId")
    fun getExerciseById(exerciseId: Int): Flow<ExerciseEntity?>

    @Insert
    suspend fun addExercise(exercise: ExerciseEntity): Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExercise(entity: ExerciseEntity)

    @Query("select e.* " +
            "from exercises e " +
            "join templateExerciseCrossRefs te on te.templateId = :id and te.exerciseId = e.id " +
            "order by te.'index' ")
    fun getExercisesByTemplateId(id: Int): Flow<List<ExerciseEntity>>
}