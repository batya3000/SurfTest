package com.batya.surftest.data.storage

import androidx.room.TypeConverter

class ListTypeConverters() {
    @TypeConverter
    fun fromList(list: List<String?>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        return string.split(",").map { it.trim() }
    }
}