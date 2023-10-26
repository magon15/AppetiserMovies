package com.appetiser.appetisermovies.data.db

import androidx.room.TypeConverter
import com.appetiser.appetisermovies.data.model.Movie
import com.google.gson.Gson

class DBConverters {
    @TypeConverter
    fun movieToJson(value: Movie?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToMovie(value: String) = Gson().fromJson(value, Movie::class.java)
}