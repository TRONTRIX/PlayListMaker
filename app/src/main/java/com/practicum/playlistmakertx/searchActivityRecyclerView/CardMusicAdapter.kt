package com.practicum.playlistmakertx.searchActivityRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmakertx.R

class CardMusicAdapter(
    var listTrack: List<Track>
): RecyclerView.Adapter<CardMusicViewHolder>() {

    private var onTrackClick: ((Track) -> Unit)? = null
    fun setOnTrackClickClickListener(listener: (Track) -> Unit) {
        onTrackClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardMusicViewHolder {
        return CardMusicViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CardMusicViewHolder, position: Int) {
        holder.bind(listTrack[position])
        holder.itemView.setOnClickListener {
            onTrackClick?.let { it1 -> it1(listTrack[position]) }
        }
    }
    override fun getItemCount(): Int {
        return listTrack.size
    }
}