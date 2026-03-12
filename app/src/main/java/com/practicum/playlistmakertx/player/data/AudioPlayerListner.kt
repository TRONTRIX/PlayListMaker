package com.practicum.playlistmakertx.player.data

interface AudioPlayerListner {
    fun onStateChanged(isPlaying: Boolean)
    fun onTimerUpdated(timeText: String)
    fun onTrackEnded()
}