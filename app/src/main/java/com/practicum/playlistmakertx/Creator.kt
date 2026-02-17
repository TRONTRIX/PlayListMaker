package com.practicum.playlistmakertx

import android.content.Context
import android.content.SharedPreferences
import com.practicum.playlistmakertx.data.SearchHistoryRepositoryImpl
import com.practicum.playlistmakertx.data.ThemeRepositoryImpl
import com.practicum.playlistmakertx.data.TrackRepositoryImpl
import com.practicum.playlistmakertx.data.network.RetrofitSearchNetworkClient
import com.practicum.playlistmakertx.domain.api.SearchHistoryRepository
import com.practicum.playlistmakertx.domain.api.SearchHistotyInteractor
import com.practicum.playlistmakertx.domain.api.ThemeInteractor
import com.practicum.playlistmakertx.domain.api.ThemeRepository
import com.practicum.playlistmakertx.domain.api.TrackRepository
import com.practicum.playlistmakertx.domain.api.TracksInteractor
import com.practicum.playlistmakertx.domain.impl.SearchHistoryInteractorImpl
import com.practicum.playlistmakertx.domain.impl.ThemeInteractorImpl
import com.practicum.playlistmakertx.domain.impl.TracksInteractorImpl


object Creator {
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    fun provideThemeInteractor(): ThemeInteractor {
        return ThemeInteractorImpl(provideThemeRepository())
    }

    private fun provideThemeRepository(): ThemeRepository {
        return ThemeRepositoryImpl(applicationContext)
    }

    private fun getTracksRepository(): TrackRepository {
        return TrackRepositoryImpl(RetrofitSearchNetworkClient())
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getTracksRepository())
    }

    fun getSearchHistoryRepository(context: Context): SearchHistoryRepository{
        return SearchHistoryRepositoryImpl(provideSearchHistorySharedPreferences(context))
    }

    fun provideSearchHistoryInteractor(context: Context): SearchHistotyInteractor{
        return SearchHistoryInteractorImpl(getSearchHistoryRepository(context))
    }



    private fun provideSearchHistorySharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "search_history_prefs",
            Context.MODE_PRIVATE
        )
    }
}