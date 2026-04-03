package com.practicum.playlistmakertx.settings.domain.api

interface ThemeInteractor {
    fun isDarkTheme(): Boolean
    fun setDarkTheme(enabled: Boolean)
}