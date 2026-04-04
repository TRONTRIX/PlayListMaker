package com.practicum.playlistmakertx.player.di

import com.practicum.playlistmakertx.player.ui.AudioPlayerViewModel
import com.practicum.playlistmakertx.search.domain.models.Track
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playerViewModel = module{
    viewModel { parameters ->
        val track = parameters.get<Track>()
        AudioPlayerViewModel(track, get())
    }
}