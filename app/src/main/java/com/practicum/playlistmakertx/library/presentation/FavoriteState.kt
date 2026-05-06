package com.practicum.playlistmakertx.library.presentation

import com.practicum.playlistmakertx.search.domain.models.Track

sealed interface  FavoriteState {
    data class FavoritesTracks(
        val listTrack: MutableList<Track>
    ) : FavoriteState

    object EmptyFavoritesTrack : FavoriteState
}