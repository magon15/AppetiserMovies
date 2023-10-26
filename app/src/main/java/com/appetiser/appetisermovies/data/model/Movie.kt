package com.appetiser.appetisermovies.data.model

import java.io.Serializable

data class Movie(
    val trackId: Int,
    val artistName: String? = "",
    val artworkUrl100: String? = "",
    val contentAdvisoryRating: String? = "",
    val currency: String? = "",
    val longDescription: String? = "",
    val primaryGenreName: String? = "",
    val trackName: String? = "",
    val trackPrice: Double? = 0.0
): Serializable {
    fun toMovieLocal(): MovieLocal {
        return MovieLocal(trackId, this)
    }
}