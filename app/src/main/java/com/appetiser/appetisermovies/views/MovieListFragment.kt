package com.appetiser.appetisermovies.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.appetiser.appetisermovies.databinding.FragmentMovieListBinding
import com.appetiser.appetisermovies.general.ResultOf
import com.appetiser.appetisermovies.view_models.MoviesViewModel
import com.appetiser.appetisermovies.view_models.SettingsViewModel
import com.appetiser.appetisermovies.views.epoxy.MovieListController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment: Fragment() {

    private val viewModel by viewModels<MoviesViewModel>()
    private val settingsViewModel by viewModels<SettingsViewModel>()
    private lateinit var moviesController: MovieListController

    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        moviesController = MovieListController(
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
            }
        )

        binding.recyclerMovies.apply {
            setController(moviesController)
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        setupObservers()

        //every time search text changes, do movie query
        binding.searchView.setupOnQueryChange { query ->
            viewModel.searchMovies(query)
            //every time query changes, save query onto shared preferences to restore query on app restore
            settingsViewModel.saveString(SettingsViewModel.LAST_SEARCH, query)
        }

        viewModel.getFavoriteMovies()

        //restore last search of user
        settingsViewModel.getLastSearch()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.searchedMovies.observe(viewLifecycleOwner) { result ->

            if(result is ResultOf.Success) {
                moviesController.updateMovies(result.value.movies)
            }else if(result is ResultOf.Failure){
                Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
            }

            //set the visibility of relevant views
            val isLoading = result is ResultOf.Loading
            binding.progressSearchMovie.visibility = if(isLoading) View.VISIBLE else View.GONE
            val hasMoviesReturned = result is ResultOf.Success && result.value.movies.isNotEmpty()
            binding.recyclerMovies.visibility = if(hasMoviesReturned) View.VISIBLE else View.GONE
            //Show 'No Movies' image when there are no movies and there are no currently running movie search task,
            //also shows when movie query returns failure.
            binding.groupNoMovies.visibility = if(!hasMoviesReturned && !isLoading) View.VISIBLE else View.GONE
        }

        //this will set the searched movies as favorited by the user
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner) {
            moviesController.updateFavoriteMovies(it)
        }

        //load last search on every app open
        settingsViewModel.lastSearch.observe(viewLifecycleOwner) {
            binding.searchView.setQuery(it)
        }
    }
}