package com.practicum.playlistmakertx.library.ui


import com.practicum.playlistmakertx.library.domain.MyPlayList

sealed interface  PlaylistState {
    object NoPlaylists : PlaylistState // нет плейлистов
    data class userPlaylists(val playlists: MutableList<MyPlayList>) : PlaylistState
}