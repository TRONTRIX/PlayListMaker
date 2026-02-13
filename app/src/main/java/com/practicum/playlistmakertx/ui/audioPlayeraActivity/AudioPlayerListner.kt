package com.practicum.playlistmakertx.ui.audioPlayeraActivity

interface AudioPlayerListner {
    fun onStateChanged(isPlaying: Boolean)
    fun onTimerUpdated(timeText: String)
    fun onTrackEnded()
}