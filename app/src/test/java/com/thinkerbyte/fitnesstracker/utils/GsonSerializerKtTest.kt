package com.thinkerbyte.fitnesstracker.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class GsonSerializerKtTest {
    @Test
    fun toJson() {
        val entity = MockEntity(1, "Test")
        val json = entity.asJson()
        assertEquals(json, """{"id":1,"description":"Test"}""")
    }

    @Test
    fun fromJsonTest() {
        val json = """{"id":1,"description":"Test"}"""
        val entity: MockEntity = fromJson(json)
        assertEquals(entity, MockEntity(1, "Test"))
    }

    data class MockEntity (
        val id: Int,
        val description: String,
    )
}