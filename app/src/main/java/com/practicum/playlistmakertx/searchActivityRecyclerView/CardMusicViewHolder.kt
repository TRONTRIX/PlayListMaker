package com.practicum.playlistmakertx.searchActivityRecyclerView

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmakertx.R
import com.practicum.playlistmakertx.searchActivityRecyclerView.Track


class CardMusicViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.music_card_in_search_activity, parent,false)
)  {

    val imageTrack: ImageView = itemView.findViewById(R.id.image_track)
    val nameTrack: TextView = itemView.findViewById(R.id.name_track)
    val artistNameTrack: TextView = itemView.findViewById(R.id.artist_name_track)
    val timeTrack: TextView = itemView.findViewById(R.id.time_track)

    fun bind(item: Track) {
        val cornerRadius = dpToPx(2f, itemView.context)
        Glide.with(itemView)
            .load(item.artworkUrl100)
            .placeholder(R.drawable.placeholder_45dp)
            .centerInside()
            .transform(RoundedCorners(cornerRadius))
            .into(imageTrack)
        nameTrack.text = item.trackName
        artistNameTrack.text = item.artistName
        timeTrack.text = item.trackTime
    }

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}