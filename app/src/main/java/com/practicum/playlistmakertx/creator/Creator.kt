package com.practicum.playlistmakertx.creator

import android.content.Context
import android.content.SharedPreferences
import com.practicum.playlistmakertx.player.data.AudioPlayerListner
import com.practicum.playlistmakertx.player.data.AudioPlayerRepositoryImpl
import com.practicum.playlistmakertx.player.domain.api.AudioPlayerInteractor
import com.practicum.playlistmakertx.player.domain.impl.AudioPlayerInteractorImpl
import com.practicum.playlistmakertx.search.data.SearchHistoryRepositoryImpl
import com.practicum.playlistmakertx.settings.data.ThemeRepositoryImpl
import com.practicum.playlistmakertx.search.domain.impl.TrackRepositoryImpl
import com.practicum.playlistmakertx.search.data.network.RetrofitSearchNetworkClient
import com.practicum.playlistmakertx.search.domain.api.SearchHistoryRepository
import com.practicum.playlistmakertx.search.domain.api.SearchHistotyInteractor
import com.practicum.playlistmakertx.settings.domain.api.ThemeInteractor
import com.practicum.playlistmakertx.settings.domain.api.ThemeRepository
import com.practicum.playlistmakertx.search.domain.api.TrackRepository
import com.practicum.playlistmakertx.search.domain.api.TracksInteractor
import com.practicum.playlistmakertx.search.domain.impl.SearchHistoryInteractorImpl
import com.practicum.playlistmakertx.settings.domain.impl.ThemeInteractorImpl
import com.practicum.playlistmakertx.search.domain.impl.TracksInteractorImpl


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

    fun getSearchHistoryRepository(context: Context): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(provideSearchHistorySharedPreferences(context))
    }

    fun provideSearchHistoryInteractor(context: Context): SearchHistotyInteractor {
        return SearchHistoryInteractorImpl(getSearchHistoryRepository(context))
    }

    fun provideAudioPlayerInteractor(listener: AudioPlayerListner): AudioPlayerInteractor {
        val repository = AudioPlayerRepositoryImpl()
        return AudioPlayerInteractorImpl(repository, listener)
    }



    private fun provideSearchHistorySharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "search_history_prefs",
            Context.MODE_PRIVATE
        )
    }
}