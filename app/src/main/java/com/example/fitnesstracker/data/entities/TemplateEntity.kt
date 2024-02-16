package com.example.fitnesstracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates")
data class TemplateEntity (
    @PrimaryKey val id: Int,
    val name: String,
    val isBaseTemplate: Boolean,
)