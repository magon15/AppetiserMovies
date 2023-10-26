package com.appetiser.appetisermovies.views.epoxy

import android.widget.ImageView
import com.airbnb.epoxy.EpoxyController
import com.appetiser.appetisermovies.R
import com.appetiser.appetisermovies.data.model.Movie
import com.appetiser.appetisermovies.data.model.MovieLocal

class MovieFavoritesController(
    private val clickListener: (Movie, Boolean, ImageView) -> Unit,
    private val favoriteListener: (Movie, Boolean) -> Unit,
    private val noItemsToShowListener: (Boolean) -> Unit
): EpoxyController() {

    private var favoriteMovies: List<MovieLocal> = listOf()
    private var recentMovies: List<MovieLocal> = listOf()

    override fun buildModels() {

        if(favoriteMovies.isNotEmpty()) {
            addHeader(R.string.favorites)
        }
        addToMovies(favoriteMovies, "FAVORITE")

        if(recentMovies.isNotEmpty()) {
            addHeader(R.string.recently_viewed)
        }
        addToMovies(recentMovies, "RECENT")

    }

    private fun addHeader(resourceString: Int) {
        movieHeader {
            spanSizeOverride { _, _, _ -> 2 }
            id(resourceString)
            header(resourceString)
        }
    }

    private fun addToMovies(movies: List<MovieLocal>, operation: String) {
        //operation string is added to prevent duplicate view ID's
        movies.map { movieLocal ->
            movie {
                id(movieLocal.trackId.toString() + operation)
                movie(movieLocal.movie)
                favorite(this@MovieFavoritesController.isFavoriteMovie(movieLocal))
                clickListener(this@MovieFavoritesController.clickListener)
                favoriteListener(this@MovieFavoritesController.favoriteListener)
            }
        }
    }

    fun updateFavoriteMovies(favoriteMovies: List<MovieLocal>) {
        this.favoriteMovies = favoriteMovies
        requestModelBuild()
        setNoItemsToShow()
    }

    fun updateRecentMovies(recentMovies: List<MovieLocal>) {
        this.recentMovies = recentMovies
        requestModelBuild()
        setNoItemsToShow()
    }

    private fun setNoItemsToShow() {
        val noItemsToShow = favoriteMovies.isEmpty() && recentMovies.isEmpty()
        noItemsToShowListener(noItemsToShow)
    }

    private fun isFavoriteMovie(movie: MovieLocal): Boolean {
        return favoriteMovies.map { favorite -> favorite.trackId }.contains(movie.trackId)
    }
}