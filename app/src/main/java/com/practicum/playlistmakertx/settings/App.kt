package com.practicum.playlistmakertx.settings

import android.app.Application
import com.practicum.playlistmakertx.creator.Creator

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Creator.init(this)

        val themeInteractor = Creator.provideThemeInteractor()
        val isDark = themeInteractor.isDarkTheme()
    }

}

