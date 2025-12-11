package com.practicum.playlistmakertx

import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmakertx.searchActivityRecyclerView.Track

class SearchHistory(private val sharedPreferences: SharedPreferences) {
    private val gson = Gson()
    private val key = "search_history"
    private val maxSize = 10

    fun getHisory(): List<Track> {
        val jsonString = sharedPreferences.getString(key, null)
        if (jsonString == null) {
            return emptyList()
        }
        return try {
            val trackArray = gson.fromJson(jsonString, Array<Track>::class.java)
            trackArray.toList()
        } catch (e: Exception){
            emptyList()
        }
    }

    fun addTrack(track: Track) {
        val history = getHisory().toMutableList()
        history.removeAll { it.trackId == track.trackId }
        history.add(0, track)
        if (history.size > maxSize){
            history.removeAt(history.size - 1)
        }

        val jsonString = gson.toJson(history.toTypedArray())
        sharedPreferences.edit()
            .putString(key, jsonString)
            .apply()

    }

    fun clearHistory() {
        sharedPreferences.edit()
            .remove(key)
            .apply()
    }

}