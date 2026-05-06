package com.practicum.playlistmakertx.library.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistViewModel : ViewModel() {
    private val stateLiveData = MutableLiveData<PlaylistState>(
        newState())


    fun observeState(): LiveData<PlaylistState> = stateLiveData

    private fun newState() : PlaylistState {



        return PlaylistState.NoPlaylists
    }
}