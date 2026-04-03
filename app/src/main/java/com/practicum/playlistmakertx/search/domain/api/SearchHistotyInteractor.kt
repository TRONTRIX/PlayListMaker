package com.practicum.playlistmakertx.search.domain.api

import com.practicum.playlistmakertx.search.domain.models.Track

interface SearchHistotyInteractor {
    fun  getHistory(): List<Track>
    fun addTrack(track: Track)
    fun clearHistory()
}