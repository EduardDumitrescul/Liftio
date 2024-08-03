package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitnesstracker.data.roomdb.entity.ExerciseEntity
import com.example.fitnesstracker.data.roomdb.entity.WorkoutExerciseCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insert(it: ExerciseEntity)

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
            "join workoutExerciseCrossRefs te on te.workoutId = :id and te.exerciseId = e.id " +
            "order by te.'index' ")
    fun getExercisesByTemplateId(id: Int): Flow<List<ExerciseEntity>>

    @Query("select te.* " +
            "from workoutExerciseCrossRefs te " +
            "where workoutId=:id")
    fun getTemplateExerciseCrossRefs(id: Int): Flow<List<WorkoutExerciseCrossRef>>
}