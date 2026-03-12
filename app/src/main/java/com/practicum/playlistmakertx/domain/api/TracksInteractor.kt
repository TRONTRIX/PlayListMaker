package com.practicum.playlistmakertx.domain.api

import com.practicum.playlistmakertx.domain.models.Track

interface TracksInteractor {
    fun searchTracks(expression: String, consumer: TracksConsumer)

    interface TracksConsumer {
        fun onSuccess(foundTracks: List<Track>)
        fun onError(errorMessage: String)
    }
}