package com.appetiser.appetisermovies.data.remote

import com.appetiser.appetisermovies.data.model.MoviesResult
import com.appetiser.appetisermovies.general.ResultOf
import com.appetiser.appetisermovies.general.safeApiCall
import com.appetiser.appetisermovies.network.MovieService
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val service: MovieService
) {
    suspend fun getMovies(searchTerm: String): ResultOf<MoviesResult> {
        return safeApiCall { service.getMovies(searchTerm) }
    }
}