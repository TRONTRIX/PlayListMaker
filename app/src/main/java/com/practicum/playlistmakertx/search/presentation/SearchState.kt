package com.practicum.playlistmakertx.search.presentation

import com.practicum.playlistmakertx.search.domain.models.Track

sealed class SearchState {
    object Idle : SearchState()                     // начальное состояние (поле пустое, ничего не показываем)
    object Loading : SearchState()
    data class Content(val tracks: List<Track>) : SearchState()  // результаты поиска
    data class History(val tracks: List<Track>) : SearchState()  // история поиска
    object Empty : SearchState()                    // ничего не найдено
    data class Error(val errorMessage: String) : SearchState()   // ошибка (vv vнапример, нет сети)
}