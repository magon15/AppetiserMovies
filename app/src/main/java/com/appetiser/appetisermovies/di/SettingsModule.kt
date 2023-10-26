package com.appetiser.appetisermovies.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.magonapps.waiterpal.data.local_data_source.SettingsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {
    @Provides
    @Singleton
    fun providesSettingsDataSource(application: Application): SettingsDataSource {
        val dataStore = application.applicationContext.settingsDataStore
        return SettingsDataSource(dataStore)
    }
}

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "com.appetiser.appetisermovies.settings"
)