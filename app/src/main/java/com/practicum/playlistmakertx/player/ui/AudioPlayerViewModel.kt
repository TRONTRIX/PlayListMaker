package com.practicum.playlistmakertx.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmakertx.creator.Creator
import com.practicum.playlistmakertx.player.data.AudioPlayerListner
import com.practicum.playlistmakertx.player.domain.api.AudioPlayerInteractor
import com.practicum.playlistmakertx.player.presentation.AudioPlayerState
import com.practicum.playlistmakertx.player.presentation.PlaybackState
import com.practicum.playlistmakertx.search.domain.models.Track

class AudioPlayerViewModel(
    private val track: Track
) : ViewModel(), AudioPlayerListner {

    private val interactor: AudioPlayerInteractor = Creator.provideAudioPlayerInteractor(this)

    private val stateLiveData = MutableLiveData<AudioPlayerState>()
    fun observeState(): LiveData<AudioPlayerState> = stateLiveData

    init {
        stateLiveData.value = AudioPlayerState(PlaybackState.PREPARING, "0:30")
        interactor.preparePlayer(track.previewUrl)
    }

    fun playPause() {
        interactor.playbackControl()
    }

    override fun onStateChanged(isPlaying: Boolean) {
        val currentState = stateLiveData.value ?: return
        val newPlaybackState = if (isPlaying) PlaybackState.PLAYING else PlaybackState.PAUSED
        stateLiveData.value = currentState.copy(playbackState = newPlaybackState)}

    override fun onTimerUpdated(timeText: String) {
        val currentState = stateLiveData.value ?: return
        stateLiveData.value = currentState.copy(timerText = timeText)
    }

    override fun onTrackEnded() {
        stateLiveData.value = AudioPlayerState(PlaybackState.PREPARED, "0:30")
    }

    fun pauseIfPlaying() {
        val currentState = stateLiveData.value
        if (currentState?.playbackState == PlaybackState.PLAYING) {
            interactor.playbackControl()
        }
    }

    override fun onCleared() {
        interactor.release()
        super.onCleared()
    }
}