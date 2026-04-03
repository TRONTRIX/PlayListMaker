package com.practicum.playlistmakertx.search.data.dto

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

data class TrackDto(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val trackId: Long,
    val collectionName: String?,
    val releaseDate: String?,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String
): Serializable{
    fun getFormattedTime(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
    }
}