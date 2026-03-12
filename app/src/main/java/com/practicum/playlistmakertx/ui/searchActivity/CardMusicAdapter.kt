package com.practicum.playlistmakertx.ui.searchActivity

import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmakertx.domain.models.Track
import com.practicum.playlistmakertx.ui.audioPlayeraActivity.AudioPlayerActivity

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

            val playerIntent = Intent(holder.itemView.context, AudioPlayerActivity::class.java)
            playerIntent.putExtra("track_from_Adapter", listTrack[position])
            holder.itemView.context.startActivity(playerIntent)
            onTrackClick?.let { it1 ->
                it1(listTrack[position]) }
        }
    }
    override fun getItemCount(): Int {
        return listTrack.size
    }
}