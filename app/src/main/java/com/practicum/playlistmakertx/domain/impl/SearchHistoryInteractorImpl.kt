package com.practicum.playlistmakertx.domain.impl

import com.practicum.playlistmakertx.data.SearchHistoryRepositoryImpl
import com.practicum.playlistmakertx.domain.api.SearchHistoryRepository
import com.practicum.playlistmakertx.domain.api.SearchHistotyInteractor
import com.practicum.playlistmakertx.domain.models.Track

class SearchHistoryInteractorImpl(
    private val repository: SearchHistoryRepository
): SearchHistotyInteractor {
    override fun getHistory(): List<Track> {
        return repository.getHistory()
    }

    override fun addTrack(track: Track) {
        repository.saveTrack(track)
    }

    override fun clearHistory() {
        repository.clear()
    }
}