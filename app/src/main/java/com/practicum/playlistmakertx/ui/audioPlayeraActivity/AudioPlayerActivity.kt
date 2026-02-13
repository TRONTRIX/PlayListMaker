package com.practicum.playlistmakertx.ui.audioPlayeraActivity

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
import com.practicum.playlistmakertx.R
import com.practicum.playlistmakertx.domain.models.Track

class AudioPlayerActivity : AppCompatActivity(), AudioPlayerListner {

    private lateinit var playButton: ImageButton
    private lateinit var timerText: TextView
    private lateinit var playerManager: AudioPlayerManager
    private var lastClickTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)


        playerManager = AudioPlayerManager(this)

        initViews()
        setupToolbar()
        loadTrack()


        playButton.setOnClickListener {
            if (System.currentTimeMillis() - lastClickTime < 500) return@setOnClickListener
            lastClickTime = System.currentTimeMillis()
            playerManager.playPause()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        playButton = findViewById(R.id.button2)
        timerText = findViewById(R.id.trial_time)

    }

    private fun setupToolbar() {
        findViewById<MaterialToolbar>(R.id.tool_bar).setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadTrack() {
        val track = intent.getSerializableExtra("track_from_Adapter") as? Track ?: run {
            finish()
            return
        }


        findViewById<TextView>(R.id.name_track).text = track.trackName
        findViewById<TextView>(R.id.artist_name_track).text = track.artistName
        findViewById<TextView>(R.id.timeTrack).text = track.getFormattedTime()
        findViewById<TextView>(R.id.album).text = track.collectionName
        findViewById<TextView>(R.id.yearRealese).text = track.releaseDate?.take(4) ?: ""
        findViewById<TextView>(R.id.primaryGenreName).text = track.primaryGenreName
        findViewById<TextView>(R.id.contry).text = track.country

        val imageTrack = findViewById<ImageView>(R.id.image_track)
        Glide.with(this)
            .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.placeholder_45dp)
            .centerInside()
            .transform(RoundedCorners(dpToPx(8f, this)))
            .into(imageTrack)


        playerManager.prepare(track.previewUrl)
    }


    override fun onStateChanged(isPlaying: Boolean) {
        val icon = if (isPlaying) R.drawable.pausebutton_84dp else R.drawable.play_button_84dp
        playButton.setImageResource(icon)
    }

    override fun onTimerUpdated(timeText: String) {
        timerText.text = timeText
    }

    override fun onTrackEnded() {

    }



    override fun onPause() {
        super.onPause()
        playerManager.pauseIfPlaying()
    }

    override fun onDestroy() {
        super.onDestroy()
        playerManager.release()
    }


    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}