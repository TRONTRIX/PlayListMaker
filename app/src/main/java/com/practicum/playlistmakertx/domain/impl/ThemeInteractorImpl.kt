package com.practicum.playlistmakertx.domain.impl

import com.practicum.playlistmakertx.domain.api.ThemeInteractor
import com.practicum.playlistmakertx.domain.api.ThemeRepository

class ThemeInteractorImpl(private val themeRepository: ThemeRepository): ThemeInteractor {
    override fun isDarkTheme(): Boolean {
        return themeRepository.isDarkTheme()
    }

    override fun setDarkTheme(enabled: Boolean) {
        return themeRepository.setDarkTheme(enabled)
    }
}