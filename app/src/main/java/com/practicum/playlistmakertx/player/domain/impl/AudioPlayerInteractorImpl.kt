package com.practicum.playlistmakertx.player.domain.impl

import com.practicum.playlistmakertx.player.data.AudioPlayerListner
import com.practicum.playlistmakertx.player.domain.api.AudioPlayerInteractor
import com.practicum.playlistmakertx.player.domain.api.AudioPlayerRepository

class AudioPlayerInteractorImpl(
    private val repository: AudioPlayerRepository,
    private val listener: AudioPlayerListner
) : AudioPlayerInteractor {

    override fun preparePlayer(url: String) {
        repository.prepare(url, listener)
    }

    override fun playbackControl() {
        repository.playPause()
    }

    override fun release() {
        repository.release()
    }

    override fun pauseIfPlaying() {
        repository.pauseIfPlaying()
    }
}