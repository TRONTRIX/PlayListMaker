package com.practicum.playlistmakertx.player.di

import com.practicum.playlistmakertx.player.data.AudioPlayerListner
import com.practicum.playlistmakertx.player.domain.api.AudioPlayerInteractor
import com.practicum.playlistmakertx.player.domain.impl.AudioPlayerInteractorImpl
import org.koin.dsl.module

val playerDomainModule = module{
    factory<AudioPlayerInteractor> {
        AudioPlayerInteractorImpl(get())
    }
}