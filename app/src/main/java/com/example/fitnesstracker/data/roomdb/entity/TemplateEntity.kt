package com.example.fitnesstracker.data.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnesstracker.data.models.Template

@Entity(tableName = "templates")
data class TemplateEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val isBaseTemplate: Boolean
)

fun TemplateEntity.toModel() = Template(
    id,
    name,
    isBaseTemplate
)