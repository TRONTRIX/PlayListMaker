package com.practicum.playlistmakertx.searchActivityRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmakertx.R

class CardMusicAdapter(
    private val listTrack: List<Track>
): RecyclerView.Adapter<CardMusicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardMusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_card_in_search_activity,parent,false)
        return CardMusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardMusicViewHolder, position: Int) {
        holder.bind(listTrack[position])
    }
    override fun getItemCount(): Int {
        return listTrack.size
    }
}