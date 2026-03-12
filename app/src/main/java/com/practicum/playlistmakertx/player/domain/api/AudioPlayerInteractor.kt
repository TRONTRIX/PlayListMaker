package com.practicum.playlistmakertx.player.domain.api


interface AudioPlayerInteractor {
    fun preparePlayer(url: String)
    fun playbackControl()
    fun release()
    fun pauseIfPlaying()
}