package com.example.fitnesstracker.data.models

data class Muscle(
    val id: Int,
    val name: String,
) {
    companion object {
        fun default() = Muscle(
            id = 0,
            name = "generic muscle"
        )
    }
}