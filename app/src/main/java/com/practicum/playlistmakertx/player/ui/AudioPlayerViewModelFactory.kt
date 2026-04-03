package com.practicum.playlistmakertx.player.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmakertx.creator.Creator
import com.practicum.playlistmakertx.search.domain.models.Track
import com.practicum.playlistmakertx.search.ui.SearchViewModel

class AudioPlayerViewModelFactory (private val track: Track) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AudioPlayerViewModel::class.java)) {
            return AudioPlayerViewModel(track) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}