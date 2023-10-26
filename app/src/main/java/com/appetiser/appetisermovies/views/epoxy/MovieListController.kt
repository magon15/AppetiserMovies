package com.appetiser.appetisermovies.views.epoxy

import android.widget.ImageView
import com.airbnb.epoxy.EpoxyController
import com.appetiser.appetisermovies.data.model.Movie
import com.appetiser.appetisermovies.data.model.MovieLocal

class MovieListController(
    private val clickListener: (Movie, Boolean, ImageView) -> Unit,
    private val favoriteListener: (Movie, Boolean) -> Unit,
): EpoxyController() {

    private var movies: List<Movie> = listOf()
    private var favoriteMovies: List<MovieLocal> = listOf()

    override fun buildModels() {
        movies.map { movie ->
            movie {
                id(movie.trackId)
                movie(movie)
                favorite(this@MovieListController.isFavoriteMovie(movie))
                clickListener(this@MovieListController.clickListener)
                favoriteListener(this@MovieListController.favoriteListener)
            }
        }
    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        requestModelBuild()
    }

    fun updateFavoriteMovies(favoriteMovies: List<MovieLocal>) {
        this.favoriteMovies = favoriteMovies
        requestModelBuild()
    }

    private fun isFavoriteMovie(movie: Movie): Boolean {
        return favoriteMovies.map { favorite -> favorite.trackId }.contains(movie.trackId)
    }
}