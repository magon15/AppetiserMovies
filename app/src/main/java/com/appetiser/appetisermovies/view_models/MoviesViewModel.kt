package com.appetiser.appetisermovies.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.appetiser.appetisermovies.data.model.Movie
import com.appetiser.appetisermovies.data.model.MoviesResult
import com.appetiser.appetisermovies.general.ResultOf
import com.appetiser.appetisermovies.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {

    private var _searchedMovies: MutableLiveData<ResultOf<MoviesResult>> = MutableLiveData()
    val searchedMovies: LiveData<ResultOf<MoviesResult>> = _searchedMovies

    private lateinit var searchJob: Job

    fun searchMovies(searchTerm: String = "") {
        _searchedMovies.value = ResultOf.Loading(true)
        cancelSearchJob()
        searchJob = viewModelScope.launch {
            _searchedMovies.value = moviesRepository.getMovies(searchTerm)
        }
    }

    private fun cancelSearchJob() {
        if(::searchJob.isInitialized) searchJob.cancel()
    }

    fun setMovieAsFavorite(isFavorite: Boolean, movie: Movie) = viewModelScope.launch {
        moviesRepository.setMovieAsFavorite(isFavorite, movie)
    }

    fun setMovieAsRecentlyViewed(movie: Movie) = viewModelScope.launch {
        moviesRepository.setMovieAsRecentlyViewed(movie)
    }

    fun getFavoriteMovies() = liveData {
        moviesRepository.getFavoriteMoviesFlow().collect {
            emit(it)
        }
    }

    fun getRecentlyViewedMovies() = liveData {
        moviesRepository.getRecentlyViewedMoviesFlow().collect {
            emit(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelSearchJob()
    }
}