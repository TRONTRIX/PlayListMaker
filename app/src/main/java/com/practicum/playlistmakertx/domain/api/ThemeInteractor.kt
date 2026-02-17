package com.practicum.playlistmakertx.domain.api

interface ThemeInteractor {
    fun isDarkTheme(): Boolean
    fun setDarkTheme(enabled: Boolean)
}