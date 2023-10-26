package com.appetiser.appetisermovies.data.local

import com.appetiser.appetisermovies.data.db.MovieLocalDao
import com.appetiser.appetisermovies.data.model.Movie
import com.appetiser.appetisermovies.data.model.MovieLocal
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val movieLocalDao: MovieLocalDao
) {
    suspend fun setMovieAsFavorite(isFavorite: Boolean, movie: Movie) {
        val movieLocal = movie.getMovieLocal()
        movieLocal.isFavorite = isFavorite
        movieLocalDao.persistMovie(
            movieLocal
        )
    }

    suspend fun setMovieAsRecentlyViewed(movie: Movie) {
        val movieLocal = movie.getMovieLocal()
        movieLocal.dateLastViewed = DateTime.now().millis
        movieLocalDao.persistMovie(
            movieLocal
        )
    }

    fun getFavoriteMoviesFlow(): Flow<List<MovieLocal>> {
        return movieLocalDao.getFavoriteMoviesFlow()
    }

    fun getRecentlyViewedMoviesFlow(): Flow<List<MovieLocal>> {
        return movieLocalDao.getRecentlyViewedMoviesFlow()
    }

    private suspend fun Movie.getMovieLocal(): MovieLocal {
        //in order to prevent overwrite of parameters of existing MovieLocal's, we must first check if it first exists in the database.
        //if not, then create new movie local.
        return movieLocalDao.getMovieLocal(trackId) ?: toMovieLocal()
    }
}