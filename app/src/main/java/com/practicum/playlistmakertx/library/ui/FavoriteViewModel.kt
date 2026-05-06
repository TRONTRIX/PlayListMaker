package com.practicum.playlistmakertx.library.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmakertx.library.presentation.FavoriteState

class FavoriteViewModel : ViewModel() {
    private val stateLiveData = MutableLiveData<FavoriteState>(
        updateState())
    fun observeState(): LiveData<FavoriteState> = stateLiveData

    private fun updateState() : FavoriteState {
        return FavoriteState.EmptyFavoritesTrack
    }
}