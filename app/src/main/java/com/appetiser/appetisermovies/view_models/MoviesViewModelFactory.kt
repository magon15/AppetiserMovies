package com.appetiser.appetisermovies.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appetiser.appetisermovies.repositories.MoviesRepository
import javax.inject.Inject

class MoviesViewModelFactory @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(moviesRepository) as T
    }

}