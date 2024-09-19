package com.thinkerbyte.fitnesstracker.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified Entity> fromJson(json: String): Entity {
    val gson = Gson()
    val classOfT = object : TypeToken<Entity>() {}.type
    return gson.fromJson(json, classOfT)
}

inline fun <reified Entity> Entity.asJson(): String {
    val gson = Gson()
    return gson.toJson(this, Entity::class.java)
}