package com.practicum.playlistmakertx.search.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmakertx.creator.Creator

class SearchViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                tracksInteractor = Creator.provideTracksInteractor(),
                historyInteractor = Creator.provideSearchHistoryInteractor(application)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}