package com.practicum.playlistmakertx.player.presentation

data class AudioPlayerState(
    val playbackState: PlaybackState,
    val timerText: String
)