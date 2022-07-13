package com.vectorinc.vectormoviesearch.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken




class Converter {

    @TypeConverter
    fun fromEntityToResult(json: String): List<Result> {
        val collectionType = object : TypeToken<List<Result?>?>() {}.type
        return Gson().fromJson(json,collectionType)
    }

    @TypeConverter
    fun fromResultListToEntity(list: List<Result>): String {
        return Gson().toJson(list)
    }
}