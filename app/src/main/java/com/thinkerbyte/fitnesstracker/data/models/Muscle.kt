package com.thinkerbyte.fitnesstracker.data.models

data class Muscle(
    val id: Int,
    val group: String,
    val name: String,
    val scientificName: String,
) {
    companion object {
        fun default() = Muscle(
            id = 0,
            name = "",
            group = "",
            scientificName = "",
        )
    }
}