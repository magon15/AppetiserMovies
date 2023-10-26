package com.appetiser.appetisermovies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = MovieLocal.DB_KEY)
data class MovieLocal(
    @PrimaryKey
    val trackId: Int,
    val movie: Movie,

    //custom parameters
    var isFavorite: Boolean = false,
    var dateLastViewed: Long? = null
): Serializable {
    companion object {
        const val DB_KEY = "movie"
    }
}