package com.practicum.playlistmakertx.settings.domain.api

interface ThemeRepository {
    fun isDarkTheme(): Boolean
    fun setDarkTheme(enabled: Boolean)
}
