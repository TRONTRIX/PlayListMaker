package com.practicum.playlistmakertx.searchActivityAPI

import com.practicum.playlistmakertx.searchActivityRecyclerView.Track

class TrackResponse (
    val resultCount: Int,
    val results: ArrayList<Track>
)