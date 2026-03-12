package com.practicum.playlistmakertx.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmakertx.creator.Creator
import com.practicum.playlistmakertx.player.data.AudioPlayerListner
import com.practicum.playlistmakertx.player.domain.api.AudioPlayerInteractor
import com.practicum.playlistmakertx.player.presentation.PlaybackState
import com.practicum.playlistmakertx.search.domain.models.Track

class AudioPlayerViewModel(
    private val track: Track
) : ViewModel(), AudioPlayerListner {

    private val interactor: AudioPlayerInteractor = Creator.provideAudioPlayerInteractor(this)

    // Состояние воспроизведения
    private val playbackStateLiveData = MutableLiveData<PlaybackState>()
    fun observePlaybackState(): LiveData<PlaybackState> = playbackStateLiveData

    // Текст таймера
    private val timerTextLiveData = MutableLiveData<String>()
    fun observeTimerText(): LiveData<String> = timerTextLiveData

    init {
        playbackStateLiveData.value = PlaybackState.PREPARING
        interactor.preparePlayer(track.previewUrl)
    }

    fun playPause() {
        interactor.playbackControl()
    }

    override fun onStateChanged(isPlaying: Boolean) {
        playbackStateLiveData.value = if (isPlaying) PlaybackState.PLAYING else PlaybackState.PAUSED
    }

    override fun onTimerUpdated(timeText: String) {
        timerTextLiveData.value = timeText
    }

    override fun onTrackEnded() {
        playbackStateLiveData.value = PlaybackState.PREPARED
        timerTextLiveData.value = "0:30"
    }

    fun pauseIfPlaying() {
        if (playbackStateLiveData.value == PlaybackState.PLAYING) {
            interactor.playbackControl()
        }
    }

    override fun onCleared() {
        interactor.release()
        super.onCleared()
    }
}