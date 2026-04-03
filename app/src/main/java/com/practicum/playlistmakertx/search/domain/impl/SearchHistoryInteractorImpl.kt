package com.practicum.playlistmakertx.search.domain.impl

import com.practicum.playlistmakertx.search.domain.api.SearchHistoryRepository
import com.practicum.playlistmakertx.search.domain.api.SearchHistotyInteractor
import com.practicum.playlistmakertx.search.domain.models.Track

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