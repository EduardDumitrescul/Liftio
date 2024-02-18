package com.example.fitnesstracker.utils

import com.google.gson.Gson

inline fun <reified Entity> fromJson(json: String, classOfT: Class<Entity>): Entity {
    val gson = Gson()
    return gson.fromJson(json, classOfT)
}

inline fun <reified Entity> Entity.asJson(): String {
    val gson = Gson()
    return gson.toJson(this, Entity::class.java)
}