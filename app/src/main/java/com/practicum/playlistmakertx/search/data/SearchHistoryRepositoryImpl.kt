package com.practicum.playlistmakertx.search.data

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.practicum.playlistmakertx.search.domain.api.SearchHistoryRepository
import com.practicum.playlistmakertx.search.domain.models.Track

class SearchHistoryRepositoryImpl(
    private val sharedPreferences: SharedPreferences, private val gson: Gson): SearchHistoryRepository {
    private val key = "search_history"
    private val maxSize = 10

    override fun getHistory(): List<Track> {
        val jsonString = sharedPreferences.getString(key, null)
        return if (jsonString == null) emptyList()
        else try {
            gson.fromJson(jsonString, Array<Track>::class.java).toList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun saveTrack(track: Track) {
        val history = getHistory().toMutableList()
        history.removeAll { it.trackId == track.trackId }

        history.add(0, track)

        if (history.size > maxSize) {
            history.removeAt(history.size - 1)
        }
        val jsonString = gson.toJson(history.toTypedArray())
        sharedPreferences.edit().putString(key, jsonString).apply()

    }

    override fun clear() {
        sharedPreferences.edit().remove(key).apply()
    }
}
