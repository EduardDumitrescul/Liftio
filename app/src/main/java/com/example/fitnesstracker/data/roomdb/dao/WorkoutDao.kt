package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.fitnesstracker.data.roomdb.entity.WorkoutEntity
import com.example.fitnesstracker.data.roomdb.entity.WorkoutExerciseCrossRefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("select * from workouts " +
            "where isTemplate is 1"
    )
    fun getTemplates(): Flow<List<WorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(it: WorkoutEntity): Long

    @Insert
    suspend fun insertWorkoutExerciseCrossRef(it: WorkoutExerciseCrossRefEntity): Long

    @Query("select w.* " +
            "from workouts w " +
            "where w.id = :templateId")
    fun getWorkoutById(templateId: Int): Flow<WorkoutEntity?>

    @Query("select count(*) " +
         "from workoutExerciseCrossRefs te " +
         "where te.workoutId = :templateId")
    suspend fun getNumberOfExercisesInWorkout(templateId: Int): Int

    @Query("select we.* " +
            "from workoutExerciseCrossRefs we " +
            "where we.workoutId = :workoutId " +
            "order by we.`index`")
    fun getWorkoutExercisesByTemplateId(workoutId: Int): Flow<List<WorkoutExerciseCrossRefEntity>>

    @Query("update workouts " +
            "set name = :templateName " +
            "where id = :templateId")
    suspend fun updateWorkoutName(templateId: Int, templateName: String)

    @Query("delete from workoutExerciseCrossRefs " +
            "where id = :templateExerciseCrossRefId")
    suspend fun removeWorkoutExerciseCrossRef(templateExerciseCrossRefId: Int)

    @Query("delete from workouts where id =:workoutId")
    suspend fun removeWorkout(workoutId: Int)

    @Update
    suspend fun update(toEntity: WorkoutEntity)

    @Query("select w.* from workouts w " +
            "where w.duration > 0")
    fun getAllWorkoutEntries(): Flow<List<WorkoutEntity>>

    @Query("select we.* from workoutExerciseCrossRefs we " +
            "where we.id = :workoutExerciseId")
    suspend fun getWorkoutExercise(workoutExerciseId: Int): WorkoutExerciseCrossRefEntity

    @Update
    suspend fun updateWorkoutExercise(entity: WorkoutExerciseCrossRefEntity)

    @Query("update workoutExerciseCrossRefs " +
            "set `index` = :newIndex " +
            "where id = :id")
    suspend fun updateWorkoutExerciseIndex(id: Int, newIndex: Int)

    @Transaction
    suspend fun updateWorkoutExerciseIndexes(newIndexesForId: List<Pair<Int, Int>>) {
        newIndexesForId.forEach {
            updateWorkoutExerciseIndex(it.first, it.second)
        }
    }
}