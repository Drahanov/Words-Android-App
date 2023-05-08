package com.words.domain.words.model

import androidx.room.TypeConverter
import java.util.*

class DateConvertor {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}