package com.example.fitnesstracker.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fitnesstracker.data.roomdb.converters.LocalDateTypeConverter
import com.example.fitnesstracker.data.roomdb.dao.ExerciseDao
import com.example.fitnesstracker.data.roomdb.dao.ExerciseMuscleDao
import com.example.fitnesstracker.data.roomdb.dao.MuscleDao
import com.example.fitnesstracker.data.roomdb.entity.ExerciseEntity
import com.example.fitnesstracker.data.roomdb.entity.ExerciseMuscleCrossRefEntity
import com.example.fitnesstracker.data.roomdb.entity.MuscleEntity

private const val TAG = "APP DATABASE"

