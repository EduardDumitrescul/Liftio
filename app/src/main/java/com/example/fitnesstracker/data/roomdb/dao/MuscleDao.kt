package com.example.fitnesstracker.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnesstracker.data.roomdb.entity.ExerciseMuscleCrossRefEntity
import com.example.fitnesstracker.data.roomdb.entity.MuscleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MuscleDao {
    @Insert
    suspend fun insert(it: MuscleEntity)

    @Query("Select name from muscles")
    fun getMuscleNames(): Flow<List<String>>

    @Query("Select * from muscles where id = :id")
    fun getMuscleById(id: Int): Flow<MuscleEntity?>

    @Query("Select m.id, m.name from exerciseMuscleCrossRef e " +
            "join muscles m on e.muscleId = m.id " +
            "where e.exerciseId = :exerciseId " +
            "and e.isPrimary = 1"
    )
    fun getPrimaryMuscleByExerciseId(exerciseId: Int): Flow<MuscleEntity?>

    @Query("Select m.id, m.name from exerciseMuscleCrossRef e " +
            "join muscles m on e.muscleId = m.id " +
            "where e.exerciseId = :exerciseId " +
            "and e.isPrimary = 0"
    )
    fun getSecondaryMusclesByExerciseId(exerciseId: Int): Flow<List<MuscleEntity>>

    @Query("select id from muscles where name like :name")
    suspend fun getMuscleIdByName(name: String): Int

    @Insert
    suspend fun insert(it: ExerciseMuscleCrossRefEntity)


    @Query("delete from exerciseMuscleCrossRef where exerciseId = :exerciseId")
    fun removeExerciseMuscleRefs(exerciseId: Int)


    @Query("select m.* " +
            "from muscles m " +
            "where exists(select '*' " +
            "   from workouts w" +
            "   join workoutExerciseCrossRefs we on we.workoutId = w.id " +
            "   join exercises e on e.id = we.exerciseId " +
            "   join exerciseMuscleCrossRef em on em.exerciseId = e.id " +
            "   where w.id = :id " +
            "       and em.muscleId = m.id" +
            "       and em.isPrimary is 1) ")
    fun getMusclesByWorkoutId(id: Int): Flow<List<MuscleEntity>>

}