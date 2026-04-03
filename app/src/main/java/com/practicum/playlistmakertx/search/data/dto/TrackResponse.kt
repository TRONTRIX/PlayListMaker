package com.practicum.playlistmakertx.search.data.dto

class TrackResponse (
    val resultCount: Int,
    val results: ArrayList<TrackDto>
): Response()