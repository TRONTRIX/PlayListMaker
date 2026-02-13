package com.practicum.playlistmakertx.domain.impl

import com.practicum.playlistmakertx.domain.api.AudioPlayerInteractor
import com.practicum.playlistmakertx.domain.api.AudioPlayerRepository

class AudioPlayerInteractorImpl(private val audioPlayerRepository: AudioPlayerRepository) : AudioPlayerInteractor {
    override fun preparePlayer() {
        audioPlayerRepository.preparePlayer()
    }

    override fun playbackControl() {
        audioPlayerRepository.playbackControl()
    }

    override fun startPlayer() {
        audioPlayerRepository.startPlayer()
    }

    override fun pausePlayer() {
        audioPlayerRepository.pausePlayer()
    }

    override fun startTimer() {
        audioPlayerRepository.startTimer()
    }

    override fun stopTimer() {
        audioPlayerRepository.stopTimer()
    }

    override fun updateTimerFromPlayer() {
        audioPlayerRepository.updateTimerFromPlayer()
    }

    override fun resetTimer() {
        audioPlayerRepository.resetTimer()
    }
}