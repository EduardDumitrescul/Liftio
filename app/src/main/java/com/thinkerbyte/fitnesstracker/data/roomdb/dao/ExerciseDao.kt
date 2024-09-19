package com.thinkerbyte.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thinkerbyte.fitnesstracker.data.roomdb.entity.ExerciseEntity
import com.thinkerbyte.fitnesstracker.data.roomdb.entity.WorkoutExerciseCrossRefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insert(it: ExerciseEntity)

    @Query("Select * from exercises order by name")
    fun getExercises(): Flow<List<ExerciseEntity>>

    @Query("Select * from exercises " +
            "where id=:exerciseId")
    fun getExerciseById(exerciseId: Int): Flow<ExerciseEntity>

    @Insert
    suspend fun addExercise(exercise: ExerciseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExercise(entity: ExerciseEntity)

    @Query("select e.* " +
            "from exercises e " +
            "join workoutExerciseCrossRefs we on we.workoutId = :id and we.exerciseId = e.id " +
            "order by we.'index' ")
    fun getExercisesByWorkoutId(id: Int): Flow<List<ExerciseEntity>>

    @Query("select we.* " +
            "from workoutExerciseCrossRefs we " +
            "where workoutId=:id " +
            "order by we.`index`")
    fun getWorkoutExerciseCrossRefs(id: Int): Flow<List<WorkoutExerciseCrossRefEntity>>
}