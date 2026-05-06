package com.practicum.playlistmakertx.library.di


import com.practicum.playlistmakertx.library.ui.FavoriteViewModel
import com.practicum.playlistmakertx.library.ui.PlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val libraryViewModelModule = module {
    viewModel {
        FavoriteViewModel()
    }

    viewModel {
        PlaylistViewModel()
    }
}