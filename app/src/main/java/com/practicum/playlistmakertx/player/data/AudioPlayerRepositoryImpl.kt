package com.practicum.playlistmakertx.player.data

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.practicum.playlistmakertx.player.domain.api.AudioPlayerRepository

class AudioPlayerRepositoryImpl : AudioPlayerRepository {

    private var mediaPlayer: MediaPlayer? = null
    private var playerState = STATE_DEFAULT
    private var trackUrl: String = ""
    private var listener: AudioPlayerListner? = null

    private val handler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null
    private var isTimerRunning = false

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val PREVIEW_DURATION = 30000L // 30 секунд
        private const val DELAY = 100L
    }

    override fun prepare(url: String, listener: AudioPlayerListner) {
        this.listener = listener
        trackUrl = url
        release() // освобождаем предыдущий плеер

        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                playerState = STATE_PREPARED
                listener.onTimerUpdated("0:30")
                listener.onStateChanged(false)
            }
            setOnCompletionListener {
                playerState = STATE_PREPARED
                stopTimer()
                mediaPlayer?.seekTo(0)
                listener.onTimerUpdated("0:30")
                listener.onStateChanged(false)
                listener.onTrackEnded()
            }
        }
    }

    override fun playPause() {
        when (playerState) {
            STATE_PLAYING -> pause()
            STATE_PREPARED, STATE_PAUSED -> play()
            STATE_DEFAULT -> prepare(trackUrl, listener!!) // если ещё не готов
        }
    }

    private fun play() {
        stopTimer()
        mediaPlayer?.let {
            if (playerState == STATE_PAUSED) {
                it.start()
            } else {
                it.seekTo(0)
                it.start()
            }
            playerState = STATE_PLAYING
            listener?.onStateChanged(true)
            startTimer()
        }
    }

    private fun pause() {
        mediaPlayer?.pause()
        playerState = STATE_PAUSED
        listener?.onStateChanged(false)
        stopTimer()
        updateTimerFromPlayer()
    }

    private fun startTimer() {
        stopTimer()
        isTimerRunning = true
        timerRunnable = object : Runnable {
            override fun run() {
                if (!isTimerRunning || playerState != STATE_PLAYING) return
                mediaPlayer?.let { mp ->
                    val current = mp.currentPosition
                    if (current < PREVIEW_DURATION) {
                        updateTimerFromPlayer()
                        handler.postDelayed(this, DELAY)
                    } else {
                        listener?.onTimerUpdated("0:00")
                        if (mp.isPlaying) {
                            mp.pause()
                            mp.seekTo(PREVIEW_DURATION.toInt())
                        }
                        isTimerRunning = false
                    }
                }
            }
        }
        handler.post(timerRunnable!!)
    }

    private fun stopTimer() {
        isTimerRunning = false
        timerRunnable?.let { handler.removeCallbacks(it) }
        timerRunnable = null
    }

    private fun updateTimerFromPlayer() {
        mediaPlayer?.let {
            val current = it.currentPosition
            val remaining = PREVIEW_DURATION - current
            if (remaining > 0) {
                val seconds = (remaining / 1000).toInt()
                val minutes = seconds / 60
                val secs = seconds % 60
                listener?.onTimerUpdated(String.format("%d:%02d", minutes, secs))
            } else {
                listener?.onTimerUpdated("0:00")
            }
        }
    }

    override fun release() {
        stopTimer()
        mediaPlayer?.release()
        mediaPlayer = null
        playerState = STATE_DEFAULT
    }

    override fun pauseIfPlaying() {
        if (playerState == STATE_PLAYING) pause()
    }
}