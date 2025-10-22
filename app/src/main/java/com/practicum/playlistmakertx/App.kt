package com.practicum.playlistmakertx

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()
        val sharedPrefs = getSharedPreferences(PREFERENCES_APP_NAME, MODE_PRIVATE)
        darkTheme = sharedPrefs.getBoolean(DARK_MODE_KEY, false)
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        val sharedPreferences = getSharedPreferences(PREFERENCES_APP_NAME, MODE_PRIVATE)
        sharedPreferences.edit()
            .putBoolean(DARK_MODE_KEY, darkThemeEnabled)
            .apply()
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}

const val PREFERENCES_APP_NAME = "Preferences_app"
const val DARK_MODE_KEY = "dark_mode"