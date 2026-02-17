package com.practicum.playlistmakertx.ui.settingActivity

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmakertx.Creator

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Creator.init(this)

        val themeInteractor = Creator.provideThemeInteractor()
        val isDark = themeInteractor.isDarkTheme()


    }

}

