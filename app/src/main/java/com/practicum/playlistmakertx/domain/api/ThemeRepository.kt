package com.practicum.playlistmakertx.domain.api

interface ThemeRepository {
    fun isDarkTheme(): Boolean
    fun setDarkTheme(enabled: Boolean)
}
