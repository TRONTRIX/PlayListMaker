package com.practicum.playlistmakertx.settings.domain.impl

import com.practicum.playlistmakertx.settings.domain.api.ThemeInteractor
import com.practicum.playlistmakertx.settings.domain.api.ThemeRepository

class ThemeInteractorImpl(private val themeRepository: ThemeRepository): ThemeInteractor,
    ThemeRepository {
    override fun isDarkTheme(): Boolean {
        return themeRepository.isDarkTheme()
    }

    override fun setDarkTheme(enabled: Boolean) {
        return themeRepository.setDarkTheme(enabled)
    }
}