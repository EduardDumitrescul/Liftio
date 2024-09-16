package com.example.fitnesstracker.utils

import androidx.room.TypeConverter
import java.time.LocalDateTime

class DateUtil {
    companion object {
        fun toDate(dateString: String): LocalDateTime {
            return LocalDateTime.parse(dateString)
        }

        fun toString(date: LocalDateTime): String {
            return date.toString()
        }
    }
}