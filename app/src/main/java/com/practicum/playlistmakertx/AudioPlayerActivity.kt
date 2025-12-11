package com.practicum.playlistmakertx

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.appbar.MaterialToolbar
import com.practicum.playlistmakertx.searchActivityRecyclerView.Track

class AudioPlayerActivity : AppCompatActivity() {

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 100L // Уменьшаем интервал для большей точности
        private const val PREVIEW_DURATION = 30000L // 30 секунд
    }

    private var playerState = STATE_DEFAULT
    private lateinit var play: ImageButton
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var trackUrl: String
    private lateinit var handler: Handler
    private lateinit var textviewTimer: TextView

    private var timerRunnable: Runnable? = null
    private var isTimerRunning = false
    private var lastClickTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audio_player)

        handler = Handler(Looper.getMainLooper())
        mediaPlayer = MediaPlayer()

        val toolBarBack = findViewById<MaterialToolbar>(R.id.tool_bar)
        toolBarBack.setNavigationOnClickListener {
            finish()
        }

        @Suppress("DEPRECATION")
        val mainTrack = intent.getSerializableExtra("track_from_Adapter") as? Track

        if (mainTrack != null) {

            val trackName = findViewById<TextView>(R.id.name_track)
            val artistName = findViewById<TextView>(R.id.artist_name_track)
            val trackTimeMillis = findViewById<TextView>(R.id.timeTrack)
            val imageTrack = findViewById<ImageView>(R.id.image_track)
            val collectionName = findViewById<TextView>(R.id.album)
            val releaseDate = findViewById<TextView>(R.id.yearRealese)
            val primaryGenreName = findViewById<TextView>(R.id.primaryGenreName)
            val country = findViewById<TextView>(R.id.contry)
            play = findViewById<ImageButton>(R.id.button2)
            textviewTimer = findViewById(R.id.trial_time)


            textviewTimer.text = "0:30"

            val currentYear = mainTrack.releaseDate.toString().take(4)

            // Заполняем данные трека
            trackName.text = mainTrack.trackName
            artistName.text = mainTrack.artistName
            trackTimeMillis.text = mainTrack.getFormattedTime()
            collectionName.text = mainTrack.collectionName
            releaseDate.text = currentYear
            primaryGenreName.text = mainTrack.primaryGenreName
            country.text = mainTrack.country

            trackUrl = mainTrack.previewUrl

            Glide.with(this)
                .load(mainTrack.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                .placeholder(R.drawable.placeholder_45dp)
                .centerInside()
                .transform(RoundedCorners(dpToPx(8f, this)))
                .into(imageTrack)

            preparePlayer()

            play.setOnClickListener {
                if (System.currentTimeMillis() - lastClickTime < 500) {
                    return@setOnClickListener
                }
                lastClickTime = System.currentTimeMillis()
                playbackControl()
            }
        } else {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    private fun preparePlayer() {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(trackUrl)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                play.isEnabled = true
                playerState = STATE_PREPARED
                resetTimer()
                textviewTimer.text = "0:30"
            }
            mediaPlayer.setOnCompletionListener {
                play.setImageResource(R.drawable.play_button_84dp)
                playerState = STATE_PREPARED
                stopTimer()
                resetTimer()
                mediaPlayer.seekTo(0)
            }
    }

    private fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
            STATE_DEFAULT -> {
                preparePlayer()
            }
        }
    }

    private fun startPlayer() {
            stopTimer()
            if (playerState == STATE_PAUSED) {
                mediaPlayer.start()
            } else {
                mediaPlayer.seekTo(0)
                mediaPlayer.start()
            }
            play.setImageResource(R.drawable.pausebutton_84dp)
            playerState = STATE_PLAYING
            startTimer()
    }

    private fun pausePlayer() {

            mediaPlayer.pause()
            play.setImageResource(R.drawable.play_button_84dp)
            playerState = STATE_PAUSED
            stopTimer()
            updateTimerFromPlayer()


    }

    private fun startTimer() {
        stopTimer()
        isTimerRunning = true

        timerRunnable = object : Runnable {
            override fun run() {
                if (!isTimerRunning || playerState != STATE_PLAYING) {
                    return
                }

                val currentPosition = mediaPlayer.currentPosition

                if (currentPosition < PREVIEW_DURATION) {

                    updateTimerFromPlayer()

                    handler.postDelayed(this, DELAY)
                } else {
                    textviewTimer.text = "0:00"
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.pause()
                        mediaPlayer.seekTo(PREVIEW_DURATION.toInt()) // Перематываем в конец
                    }
                    isTimerRunning = false
                }
            }
        }

        handler.post(timerRunnable!!)
    }

    private fun stopTimer() {
        isTimerRunning = false
        timerRunnable?.let {
            handler.removeCallbacks(it)
        }
        timerRunnable = null
    }

    private fun updateTimerFromPlayer() {
        val currentPosition = mediaPlayer.currentPosition
        val remainingTime = PREVIEW_DURATION - currentPosition

        if (remainingTime > 0) {
            val seconds = (remainingTime / 1000).toInt()
            val minutes = seconds / 60
            val secs = seconds % 60
            textviewTimer.text = String.format("%d:%02d", minutes, secs)
        } else {
            textviewTimer.text = "0:00"
        }
    }

    private fun resetTimer() {
        stopTimer()
        textviewTimer.text = "0:30"
    }

    override fun onPause() {
        super.onPause()
        if (playerState == STATE_PLAYING) {
            pausePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (playerState == STATE_PLAYING) {
            pausePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }
}