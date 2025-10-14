package com.practicum.playlistmakertx.searchActivityRecyclerView

import java.text.SimpleDateFormat
import java.util.Locale

data class Track(
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя
    val trackTimeMillis: Long, // Продолжительность трека
    val artworkUrl100: String // Ссылка на изображение обложки
){
    fun getFormattedTime(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
    }
}
