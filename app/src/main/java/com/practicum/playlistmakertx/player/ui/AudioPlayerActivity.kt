package com.practicum.playlistmakertx.player.ui

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.appbar.MaterialToolbar
import com.practicum.playlistmakertx.R

import com.practicum.playlistmakertx.search.domain.models.Track
import com.practicum.playlistmakertx.player.data.AudioPlayerListner
import com.practicum.playlistmakertx.player.data.AudioPlayerRepositoryImpl
import com.practicum.playlistmakertx.player.domain.api.AudioPlayerInteractor
import com.practicum.playlistmakertx.player.presentation.PlaybackState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AudioPlayerActivity : AppCompatActivity() {

    private val viewModel: AudioPlayerViewModel by viewModel {
        val track = intent.getSerializableExtra("track_from_Adapter") as? Track
            ?: throw IllegalArgumentException("Track not found")
        parametersOf(track)
    }
    private lateinit var playButton: ImageButton
    private lateinit var timerText: TextView
    private var lastClickTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audio_player)

        initViews()
        setupToolbar()
        setupViewModel()
        observeViewModel()
        loadTrackInfo()

        playButton.setOnClickListener {
            if (System.currentTimeMillis() - lastClickTime < 500) return@setOnClickListener
            lastClickTime = System.currentTimeMillis()
            viewModel.playPause()
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

    private fun setupViewModel() {
        val track = intent.getSerializableExtra("track_from_Adapter") as? Track
            ?: run {
                finish()
                return
            }

    }

    private fun observeViewModel() {
        viewModel.observeState().observe(this) { state ->
            val icon = when (state.playbackState) {
                PlaybackState.PLAYING -> R.drawable.pausebutton_84dp
                else -> R.drawable.play_button_84dp
            }
            playButton.setImageResource(icon)

            timerText.text = state.timerText
        }
    }

    private fun loadTrackInfo() {
        val track = intent.getSerializableExtra("track_from_Adapter") as? Track ?: return

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
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseIfPlaying()
    }


    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}