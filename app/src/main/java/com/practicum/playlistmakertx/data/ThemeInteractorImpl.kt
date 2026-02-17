package com.practicum.playlistmakertx.data

import android.app.Application.MODE_PRIVATE
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmakertx.domain.api.ThemeRepository

class ThemeRepositoryImpl(context: Context) : ThemeRepository {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(/* p0 = */
        PREFERENCES_APP_NAME, /* p1 = */
        MODE_PRIVATE
    )

    override fun isDarkTheme(): Boolean {
        return sharedPreferences.getBoolean(DARK_MODE_KEY, false)
    }

    override fun setDarkTheme(darkThemeEnabled: Boolean) {
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