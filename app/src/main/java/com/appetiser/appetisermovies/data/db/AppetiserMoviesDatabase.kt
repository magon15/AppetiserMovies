package com.appetiser.appetisermovies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appetiser.appetisermovies.data.model.MovieLocal

@Database(entities = [MovieLocal::class], version = 1)
@TypeConverters(DBConverters::class)
abstract class AppetiserMoviesDatabase: RoomDatabase() {
    abstract fun getMovieLocalDao(): MovieLocalDao
}
