package com.practicum.playlistmakertx.settings.di

import com.practicum.playlistmakertx.settings.ui.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingViewModelModule = module {
    viewModel { SettingViewModel(get()) }
}