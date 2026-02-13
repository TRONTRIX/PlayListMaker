package com.practicum.playlistmakertx.data.dto

import com.practicum.playlistmakertx.domain.models.Track
import com.practicum.playlistmakertx.domain.models.TrackDto

class TrackResponse (
    val resultCount: Int,
    val results: ArrayList<TrackDto>
): Response()