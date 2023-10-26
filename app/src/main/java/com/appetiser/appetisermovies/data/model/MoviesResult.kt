package com.appetiser.appetisermovies.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesResult(
    val resultCount: Int,
    @SerializedName("results")
    val movies: List<Movie>
): Serializable