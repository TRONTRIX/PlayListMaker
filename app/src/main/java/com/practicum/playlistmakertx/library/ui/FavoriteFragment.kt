package com.practicum.playlistmakertx.library.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.practicum.playlistmakertx.R
import com.practicum.playlistmakertx.library.presentation.FavoriteState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private val viewModel: FavoriteViewModel by viewModel()

    private var textView: TextView? = null
    private var imageView: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragmentfavorite, container, false)
        textView = view.findViewById(R.id.textView)
        imageView = view.findViewById(R.id.imageView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            if (state is FavoriteState.EmptyFavoritesTrack)
                 {
                    textView?.visibility = View.VISIBLE
                    imageView?.visibility = View.VISIBLE
                }
                else {
                    textView?.visibility = View.GONE
                    imageView?.visibility = View.GONE
                }
        }
    }
}