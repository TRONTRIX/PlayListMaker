package com.practicum.playlistmakertx.domain.api


interface AudioPlayerInteractor {
    fun preparePlayer()

    fun playbackControl()

    fun startPlayer()

    fun pausePlayer()

    fun startTimer()

    fun stopTimer()

    fun updateTimerFromPlayer()

    fun resetTimer()


}