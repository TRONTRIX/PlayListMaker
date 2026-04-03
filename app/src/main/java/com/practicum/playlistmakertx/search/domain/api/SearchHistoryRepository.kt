package com.practicum.playlistmakertx.search.domain.api

import com.practicum.playlistmakertx.search.domain.models.Track

interface SearchHistoryRepository {

    fun  getHistory(): List<Track>
    fun saveTrack(track: Track)
    fun clear()
}
