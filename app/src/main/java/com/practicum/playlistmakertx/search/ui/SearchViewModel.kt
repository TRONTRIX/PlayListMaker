package com.practicum.playlistmakertx.search.ui

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmakertx.search.domain.api.SearchHistotyInteractor
import com.practicum.playlistmakertx.search.domain.api.TracksInteractor
import com.practicum.playlistmakertx.search.domain.models.Track
import com.practicum.playlistmakertx.search.presentation.SearchState

class SearchViewModel(
    private val tracksInteractor: TracksInteractor,
    private val historyInteractor: SearchHistotyInteractor
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
    }

    private val stateLiveData = MutableLiveData<SearchState>()
    fun observeState(): LiveData<SearchState> = stateLiveData

    private var latestSearchText: String = ""
    private var hasFocus: Boolean = false

    private val handler = Handler(Looper.getMainLooper())

    init {
        stateLiveData.value = SearchState.Idle
    }

    fun onQueryChanged(text: String) {
        latestSearchText = text
        searchDebounce(text)
    }

    fun onFocusChanged(focused: Boolean) {
        hasFocus = focused
        updateHistoryVisibility()
    }

    fun onSearchAction() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        if (latestSearchText.isNotEmpty()) {
            searchRequest(latestSearchText)
        }
    }

    fun onClearClick() {
        latestSearchText = ""
        stateLiveData.value = SearchState.Idle
        updateHistoryVisibility()
    }

    fun onTrackClick(track: Track) {
        historyInteractor.addTrack(track)
        updateHistory()
    }

    fun onClearHistoryClick() {
        historyInteractor.clearHistory()
        updateHistory() // теперь updateHistory сама скроет историю, если она пуста
    }

    fun onRetryClick() {
        if (latestSearchText.isNotEmpty()) {
            searchRequest(latestSearchText)
        }
    }

    private fun searchDebounce(text: String) {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        if (text.isEmpty()) {
            stateLiveData.value = SearchState.Idle
            updateHistoryVisibility()
            return
        }

        val searchRunnable = Runnable { searchRequest(text) }
        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(searchRunnable, SEARCH_REQUEST_TOKEN, postTime)
    }

    private fun searchRequest(query: String) {
        stateLiveData.value = SearchState.Loading

        tracksInteractor.searchTracks(query, object : TracksInteractor.TracksConsumer {
            override fun onSuccess(foundTracks: List<Track>) {
                handler.post {
                    if (query == latestSearchText) {
                        if (foundTracks.isNotEmpty()) {
                            stateLiveData.value = SearchState.Content(foundTracks)
                        } else {
                            stateLiveData.value = SearchState.Empty
                        }
                    }
                }
            }

            override fun onError(errorMessage: String) {
                handler.post {
                    if (query == latestSearchText) {
                        stateLiveData.value = SearchState.Error(errorMessage)
                    }
                }
            }
        })
    }

    private fun updateHistory() {
        val history = historyInteractor.getHistory()
        if (history.isNotEmpty() && latestSearchText.isEmpty() && hasFocus) {
            stateLiveData.value = SearchState.History(history)
        } else {
            // Если история пуста или условия не выполнены, скрываем историю
            if (stateLiveData.value is SearchState.History) {
                stateLiveData.value = SearchState.Idle
            }
        }
    }

    private fun updateHistoryVisibility() {
        if (latestSearchText.isEmpty() && hasFocus) {
            val history = historyInteractor.getHistory()
            if (history.isNotEmpty()) {
                stateLiveData.value = SearchState.History(history)
            } else {
                stateLiveData.value = SearchState.Idle
            }
        } else {
            if (stateLiveData.value is SearchState.History) {
                stateLiveData.value = SearchState.Idle
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }
    fun restoreText(text: String) {
        latestSearchText = text
    }
}