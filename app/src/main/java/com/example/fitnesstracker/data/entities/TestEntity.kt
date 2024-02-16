package com.example.fitnesstracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tests")
data class TestEntity (
    @PrimaryKey val id: UUID
)