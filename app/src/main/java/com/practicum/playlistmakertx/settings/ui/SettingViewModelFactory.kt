package com.practicum.playlistmakertx.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmakertx.creator.Creator

class SettingViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(
                themeInteractor = Creator.provideThemeInteractor()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}