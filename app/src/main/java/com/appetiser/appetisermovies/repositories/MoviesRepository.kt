package com.appetiser.appetisermovies.repositories

import com.appetiser.appetisermovies.data.local.MoviesLocalDataSource
import com.appetiser.appetisermovies.data.model.Movie
import com.appetiser.appetisermovies.data.model.MovieLocal
import com.appetiser.appetisermovies.data.model.MoviesResult
import com.appetiser.appetisermovies.data.remote.MoviesRemoteDataSource
import com.appetiser.appetisermovies.general.ResultOf
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource
) {
    suspend fun getMovies(searchTerm: String): ResultOf<MoviesResult> {
        return remoteDataSource.getMovies(searchTerm)
    }

    suspend fun setMovieAsFavorite(isFavorite: Boolean, movie: Movie) {
        localDataSource.setMovieAsFavorite(isFavorite, movie)
    }

    suspend fun setMovieAsRecentlyViewed(movie: Movie) {
        localDataSource.setMovieAsRecentlyViewed(movie)
    }

    fun getFavoriteMoviesFlow(): Flow<List<MovieLocal>> {
        return localDataSource.getFavoriteMoviesFlow()
    }

    fun getRecentlyViewedMoviesFlow(): Flow<List<MovieLocal>> {
        return localDataSource.getRecentlyViewedMoviesFlow()
    }

}