package com.appetiser.appetisermovies.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.appetiser.appetisermovies.databinding.FragmentMovieFavoritesBinding
import com.appetiser.appetisermovies.view_models.MoviesViewModel
import com.appetiser.appetisermovies.views.epoxy.MovieFavoritesController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFavoritesFragment: Fragment() {

    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var binding: FragmentMovieFavoritesBinding

    private lateinit var moviesFavoriteController: MovieFavoritesController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieFavoritesBinding.inflate(inflater, container, false)

        moviesFavoriteController = MovieFavoritesController(
            clickListener = { movie, isFavorite, imageView ->
                //show movie details activity on movie click
                val intent = Intent(requireActivity(), MovieDetailsActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), imageView, ViewCompat.getTransitionName(imageView)!!)
                intent.putExtra(MovieDetailsActivity.MOVIE, movie)
                intent.putExtra(MovieDetailsActivity.FAVORITE, isFavorite)
                startActivity(intent, options.toBundle())
            },
            favoriteListener = { movie, isFavorite ->
                viewModel.setMovieAsFavorite(isFavorite, movie)
            },
            noItemsToShowListener = { noMoviesToShow ->
                binding.groupNoMovies.visibility = if(noMoviesToShow) View.VISIBLE else View.GONE
            }
        )

        binding.recyclerMovieFavorites.apply {
            setController(moviesFavoriteController)
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner) {
            moviesFavoriteController.updateFavoriteMovies(it)
        }

        //this will show the recently viewed movies, which is at a limit of 10 queries as determined by our ROOM database query
        viewModel.getRecentlyViewedMovies().observe(viewLifecycleOwner) {
            moviesFavoriteController.updateRecentMovies(it)
        }
    }
}