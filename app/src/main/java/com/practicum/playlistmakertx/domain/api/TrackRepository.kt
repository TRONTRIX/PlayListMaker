package com.practicum.playlistmakertx.domain.api

import com.practicum.playlistmakertx.domain.models.Track

interface TrackRepository {
    fun searchTrack(expression: String): List<Track>
    fun setCodeError(): Int
}