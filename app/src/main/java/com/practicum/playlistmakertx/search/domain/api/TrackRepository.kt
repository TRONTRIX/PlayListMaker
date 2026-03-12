package com.practicum.playlistmakertx.search.domain.api

import com.practicum.playlistmakertx.search.domain.models.Track

interface TrackRepository {
    fun searchTrack(expression: String): List<Track>
    fun setCodeError(): Int
}