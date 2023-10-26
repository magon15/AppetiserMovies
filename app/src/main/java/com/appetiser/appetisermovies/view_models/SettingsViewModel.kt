package com.appetiser.appetisermovies.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appetiser.appetisermovies.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {

    companion object {
        const val LAST_SEARCH = "LAST_SEARCH"
    }

    private var _lastSearch: MutableLiveData<String> = MutableLiveData()
    val lastSearch: LiveData<String> = _lastSearch

    fun getLastSearch() = viewModelScope.launch {
        _lastSearch.value = settingsRepository.getString(LAST_SEARCH) ?: ""
    }

    fun saveString(key: String, recentlySearched: String) = viewModelScope.launch {
        settingsRepository.saveString(key, recentlySearched)
    }

}