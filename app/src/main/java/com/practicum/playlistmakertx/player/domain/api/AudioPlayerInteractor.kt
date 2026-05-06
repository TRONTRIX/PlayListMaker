package com.practicum.playlistmakertx.player.domain.api

import com.practicum.playlistmakertx.player.data.AudioPlayerListner


interface AudioPlayerInteractor {
    fun preparePlayer(url: String, listener: AudioPlayerListner)
    fun playbackControl()
    fun release()
    fun pauseIfPlaying()
}