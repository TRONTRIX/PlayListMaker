package com.practicum.playlistmakertx.search.domain.api

import com.practicum.playlistmakertx.search.domain.models.Track

interface TracksInteractor {
    fun searchTracks(expression: String, consumer: TracksConsumer)

    interface TracksConsumer {
        fun onSuccess(foundTracks: List<Track>)
        fun onError(errorMessage: String)
    }
}