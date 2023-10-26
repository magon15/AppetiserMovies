package com.appetiser.appetisermovies.di

import android.app.Application
import androidx.room.Room
import com.appetiser.appetisermovies.data.db.AppetiserMoviesDatabase
import com.appetiser.appetisermovies.data.db.MovieLocalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providesWaiterPalDatabase(application: Application): AppetiserMoviesDatabase {
        return Room.databaseBuilder(application, AppetiserMoviesDatabase::class.java, "appetiser_movies_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesMovieDao(database: AppetiserMoviesDatabase): MovieLocalDao{
        return database.getMovieLocalDao()
    }
}