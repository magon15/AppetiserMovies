package com.appetiser.appetisermovies.repositories

import com.magonapps.waiterpal.data.local_data_source.SettingsDataSource
import javax.inject.Inject

class SettingsRepository @Inject constructor (private val dataSource: SettingsDataSource) {
    suspend fun saveString(key: String, value: String) {
        dataSource.saveString(key, value)
    }

    suspend fun getString(key: String): String? {
        return dataSource.getString(key)
    }
}