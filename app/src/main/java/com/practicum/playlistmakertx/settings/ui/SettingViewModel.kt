package com.practicum.playlistmakertx.settings.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmakertx.settings.domain.api.ThemeInteractor

class SettingViewModel(
    private val themeInteractor: ThemeInteractor
) : ViewModel() {

    private val themeStateLiveData = MutableLiveData<Boolean>()
    fun observeThemeState(): LiveData<Boolean> = themeStateLiveData

    init {
        themeStateLiveData.value = themeInteractor.isDarkTheme()
    }

    fun onThemeToggled(isDark: Boolean) {
        themeInteractor.setDarkTheme(isDark)
        themeStateLiveData.value = isDark
    }
}