package com.practicum.playlistmakertx.domain.api

import com.practicum.playlistmakertx.domain.models.Track

interface SearchHistotyInteractor {
    fun  getHistory(): List<Track>
    fun addTrack(track: Track)
    fun clearHistory()
}