package com.practicum.playlistmakertx.settings

import android.app.Application
import android.content.Context

import com.practicum.playlistmakertx.di.searchDataModule
import com.practicum.playlistmakertx.di.searchDomainModule
import com.practicum.playlistmakertx.di.searchViewModelModule
import com.practicum.playlistmakertx.player.di.playerDataModule
import com.practicum.playlistmakertx.player.di.playerDomainModule
import com.practicum.playlistmakertx.player.di.playerViewModel
import com.practicum.playlistmakertx.settings.di.settingDataModule
import com.practicum.playlistmakertx.settings.di.settingDomainModule
import com.practicum.playlistmakertx.settings.di.settingViewModelModule
import networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class App : Application() {


    override fun onCreate() {
        super.onCreate()



        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                searchDataModule,
                searchDomainModule,
                searchViewModelModule,
                playerDomainModule,
                playerDataModule,
                playerViewModel,
                settingDataModule,
                settingDomainModule,
                settingViewModelModule
            )
        }
    }

}

