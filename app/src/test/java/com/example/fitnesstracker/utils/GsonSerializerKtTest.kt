package com.example.fitnesstracker.utils

import com.example.fitnesstracker.data.entities.TestEntity
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.util.UUID

class GsonSerializerKtTest {
    @Test
    fun toJson() {
        val entity = MockEntity(1, "Test")
        val json = entity.asJson()
        assertEquals(json, """{"id":1,"description":"Test"}""")
    }

    @Test
    fun fromJson() {
        val json = """{"id":1,"description":"Test"}"""
        val entity = fromJson(json, MockEntity::class.java)
        assertEquals(entity, MockEntity(1, "Test"))
    }

    data class MockEntity (
        val id: Int,
        val description: String,
    )
}