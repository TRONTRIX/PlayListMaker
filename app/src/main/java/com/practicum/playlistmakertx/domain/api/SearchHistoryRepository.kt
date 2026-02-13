package com.practicum.playlistmakertx.domain.api

import com.practicum.playlistmakertx.domain.models.Track

interface SearchHistoryRepository {

    fun  getHistory(): List<Track>
    fun saveTrack(track: Track)
    fun clear()
}
