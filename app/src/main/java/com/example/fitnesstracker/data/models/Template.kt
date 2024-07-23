package com.example.fitnesstracker.data.models

data class Template(
    val id: Int,
    val name: String,
    val isBaseTemplate: Boolean
) {
    companion object {
        fun default() = Template(
            id = 0,
            name = "default template",
            isBaseTemplate = false,
        )
    }
}
