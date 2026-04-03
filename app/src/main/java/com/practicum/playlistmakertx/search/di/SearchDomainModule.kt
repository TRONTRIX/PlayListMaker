package com.practicum.playlistmakertx.di

import com.practicum.playlistmakertx.search.domain.api.SearchHistotyInteractor
import com.practicum.playlistmakertx.search.domain.api.TracksInteractor
import com.practicum.playlistmakertx.search.domain.impl.SearchHistoryInteractorImpl
import com.practicum.playlistmakertx.search.domain.impl.TracksInteractorImpl
import org.koin.dsl.module

val searchDomainModule = module {
    single<SearchHistotyInteractor> {
        SearchHistoryInteractorImpl(get())
    }
    single<TracksInteractor> {
        TracksInteractorImpl(get())
    }
}