package com.practicum.playlistmakertx.search.data

import com.practicum.playlistmakertx.search.data.dto.TrackResponse
import com.practicum.playlistmakertx.search.data.dto.TrackSearchRequest
import com.practicum.playlistmakertx.search.domain.api.TrackRepository
import com.practicum.playlistmakertx.search.domain.models.Track

class TrackRepositoryImpl(private val networkClient: NetworkClient) : TrackRepository {
    var checkError: Int = 400

    override fun searchTrack(expression: String): List<Track> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        if (response.resultCode == 200) {
            checkError = 200
            return (response as TrackResponse).results.map {
                Track(
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis, // Продолжительность трека
                    it.artworkUrl100,// Ссылка на изображение обложки
                    it.trackId,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl
                )
            }
        } else {
            checkError = 300
            return emptyList()
        }
    }

    override fun setCodeError(): Int {
        return checkError
    }

}