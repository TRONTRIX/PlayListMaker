package com.practicum.playlistmakertx.settings.di

import com.practicum.playlistmakertx.settings.domain.api.ThemeInteractor
import com.practicum.playlistmakertx.settings.domain.impl.ThemeInteractorImpl
import org.koin.dsl.module

val settingDomainModule = module {
    single<ThemeInteractor> {
        ThemeInteractorImpl(get())
    }
}