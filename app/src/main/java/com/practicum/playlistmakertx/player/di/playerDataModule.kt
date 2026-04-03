package com.practicum.playlistmakertx.player.di

import com.practicum.playlistmakertx.player.data.AudioPlayerRepositoryImpl
import com.practicum.playlistmakertx.player.domain.api.AudioPlayerRepository
import com.practicum.playlistmakertx.search.data.TrackRepositoryImpl
import com.practicum.playlistmakertx.search.domain.api.TrackRepository
import org.koin.dsl.module

val playerDataModule = module {
    single<AudioPlayerRepository> {
        AudioPlayerRepositoryImpl()
    }
}