package com.practicum.playlistmakertx.player.domain.api

import com.practicum.playlistmakertx.player.data.AudioPlayerListner

interface AudioPlayerRepository {
    fun prepare(url: String, listener: AudioPlayerListner)
    fun playPause()
    fun release()
    fun pauseIfPlaying()
}