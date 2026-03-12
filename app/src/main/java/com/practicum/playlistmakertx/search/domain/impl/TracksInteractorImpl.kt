package com.practicum.playlistmakertx.search.domain.impl


import android.os.Handler
import android.os.Looper
import com.practicum.playlistmakertx.search.domain.api.TrackRepository
import com.practicum.playlistmakertx.search.domain.api.TracksInteractor
import java.util.concurrent.Executors

class TracksInteractorImpl(private val trackRepository: TrackRepository): TracksInteractor {
    private val executor = Executors.newCachedThreadPool()
    private var errorCode = trackRepository.setCodeError()
    private val handler = Handler(Looper.getMainLooper())

    override fun searchTracks(expression: String, consumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            try {
                val tracks = trackRepository.searchTrack(expression)
                handler.post { consumer.onSuccess(tracks) }
            } catch (e: Exception) {
                handler.post { consumer.onError(e.message ?: "Network error") }
            }
        }
    }

}