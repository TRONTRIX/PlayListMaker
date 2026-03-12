package com.practicum.playlistmakertx.search.ui

import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmakertx.search.domain.models.Track
import com.practicum.playlistmakertx.player.ui.AudioPlayerActivity

class CardMusicAdapter(
    var listTrack: List<Track>
) : RecyclerView.Adapter<CardMusicViewHolder>() {

    private var onTrackClick: ((Track) -> Unit)? = null

    fun setOnTrackClickClickListener(listener: (Track) -> Unit) {
        onTrackClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardMusicViewHolder {
        return CardMusicViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CardMusicViewHolder, position: Int) {
        val track = listTrack[position]
        holder.bind(track)
        holder.itemView.setOnClickListener {
            onTrackClick?.invoke(track)
        }
    }

    override fun getItemCount(): Int = listTrack.size
}