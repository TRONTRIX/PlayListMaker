package com.practicum.playlistmakertx.di

import android.content.Context
import com.google.gson.Gson
import com.practicum.playlistmakertx.search.data.SearchHistoryRepositoryImpl
import com.practicum.playlistmakertx.search.data.TrackRepositoryImpl
import com.practicum.playlistmakertx.search.domain.api.SearchHistoryRepository
import com.practicum.playlistmakertx.search.domain.api.TrackRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val searchDataModule = module {
    single {
        androidContext().getSharedPreferences("playlist_maker_prefs", Context.MODE_PRIVATE)
    }
    factory { Gson() }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get(), get())
    }
    single<TrackRepository> {
        TrackRepositoryImpl(get())
    }
}