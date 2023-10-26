package com.appetiser.appetisermovies.network

import com.appetiser.appetisermovies.data.model.MoviesResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("search")
    suspend fun getMovies(
        @Query("term") searchTerm: String,
        @Query("country") country: String = "au",
        @Query("media") media: String = "movie"
    ): MoviesResult
}