package com.practicum.playlistmakertx

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.os.Bundle
import android.util.TypedValue
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

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audio_player)

        val toolBarBack = findViewById<MaterialToolbar>(R.id.tool_bar)
        toolBarBack.setNavigationOnClickListener{
            finish()
        }

        @Suppress("DEPRECATION")
        val mainTarck = intent.getSerializableExtra("track_from_Adapter") as? Track

        if (mainTarck != null){
            //val textTrack = findViewById<TextView>(R.id.text1111)
            //textTrack.text = mainTarck.trackName

            val trackName = findViewById<TextView>(R.id.name_track) // Название композиции
            val artistName = findViewById<TextView>(R.id.artist_name_track) // Имя исполнителя
            val trackTimeMillis = findViewById<TextView>(R.id.timeTrack) // Продолжительность трека
            val imageTrack = findViewById<ImageView>(R.id.image_track)// Ссылка на изображение обложки
            val collectionName = findViewById<TextView>(R.id.album)
            val releaseDate = findViewById<TextView>(R.id.yearRealese)
            val primaryGenreName = findViewById<TextView>(R.id.primaryGenreName)
            val country = findViewById<TextView>(R.id.contry)

            val currentYear = mainTarck.releaseDate.toString().take(4)

            trackName.setText(mainTarck.trackName)
            artistName.setText(mainTarck.artistName)
            trackTimeMillis.setText(mainTarck.getFormattedTime())
            collectionName.setText(mainTarck.collectionName)
            releaseDate.setText(currentYear)
            primaryGenreName.setText(mainTarck.primaryGenreName)
            country.setText(mainTarck.country)

            Glide.with(this)
                .load(mainTarck.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                .placeholder(R.drawable.placeholder_45dp)
                .centerInside()
                .transform(RoundedCorners(dpToPx(8f, this)))
                .into(imageTrack)
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
}