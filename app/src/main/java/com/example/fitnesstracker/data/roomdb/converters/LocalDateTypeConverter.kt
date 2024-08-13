package com.example.fitnesstracker.data.roomdb.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime


object LocalDateTypeConverter {
    @TypeConverter
    fun toDate(dateString: String?): LocalDateTime? {
        return if (dateString == null) {
            null
        } else {
            LocalDateTime.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDateTime?): String? {
        return date?.toString()
    }
}