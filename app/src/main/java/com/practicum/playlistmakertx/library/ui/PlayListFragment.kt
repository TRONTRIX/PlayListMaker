package com.practicum.playlistmakertx.library.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.practicum.playlistmakertx.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : Fragment() {

    companion object {
        fun newInstance() = PlaylistsFragment()
    }
    private var textView: TextView? = null
    private var imageView: ImageView? = null
    private val viewModel: PlaylistViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragmentplaylist, container, false)
        imageView = view.findViewById(R.id.imageView)
        textView = view.findViewById(R.id.textView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            if (state is PlaylistState.NoPlaylists) {
                    textView?.visibility = View.VISIBLE
                    imageView?.visibility = View.VISIBLE
                }
                else {
                    textView?.visibility = View.GONE
                    imageView?.visibility = View.GONE
                }

        }
    }
    //
}