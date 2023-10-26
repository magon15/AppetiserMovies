package com.appetiser.appetisermovies.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.appetiser.appetisermovies.data.model.MovieLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieLocalDao {
    @Upsert
    suspend fun persistMovie(movie: MovieLocal)

    @Query("SELECT * FROM ${MovieLocal.DB_KEY} WHERE isFavorite = 1")
    fun getFavoriteMoviesFlow(): Flow<List<MovieLocal>>

    @Query("SELECT * FROM ${MovieLocal.DB_KEY} WHERE dateLastViewed IS NOT NULL ORDER BY dateLastViewed DESC LIMIT 10")
    fun getRecentlyViewedMoviesFlow(): Flow<List<MovieLocal>>

    @Query("SELECT * FROM ${MovieLocal.DB_KEY} WHERE trackId = :id")
    suspend fun getMovieLocal(id: Int): MovieLocal?

    @Query("SELECT * FROM ${MovieLocal.DB_KEY} WHERE trackId = :id")
    fun getMovieLocalFlow(id: Int): Flow<MovieLocal?>
}