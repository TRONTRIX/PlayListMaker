package com.practicum.playlistmakertx.settings.di

import com.practicum.playlistmakertx.settings.data.ThemeRepositoryImpl
import com.practicum.playlistmakertx.settings.domain.api.ThemeRepository
import com.practicum.playlistmakertx.settings.domain.impl.ThemeInteractorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val settingDataModule = module {
    single<ThemeRepository> {
        ThemeRepositoryImpl(androidContext())
    }
}