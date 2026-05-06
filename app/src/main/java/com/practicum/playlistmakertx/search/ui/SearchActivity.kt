package com.practicum.playlistmakertx.search.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.practicum.playlistmakertx.R
import com.practicum.playlistmakertx.player.ui.AudioPlayerActivity
import com.practicum.playlistmakertx.search.presentation.SearchState
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity() {


    private val viewModel: SearchViewModel by viewModel()
    private var isRestoring = false

    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewHistory: RecyclerView
    private lateinit var historyLinear: LinearLayout
    private lateinit var emptyState: LinearLayout
    private lateinit var networkLostError: LinearLayout
    private lateinit var updateErrorButtonSearch: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var clearHistoryButton: Button
    private lateinit var cardMusicAdapter: CardMusicAdapter
    private lateinit var historyCardMusicAdapter: CardMusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        initViews()
        setupToolbar()
        setupAdapters()
        //setupViewModel()
        observeViewModel()
        setupListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null) {
            isRestoring = true
            val savedText = savedInstanceState.getString(SEARCH_TEXT, "")
            viewModel.restoreText(savedText)
            inputEditText.setText(savedText)
            clearButton.visibility = if (savedText.isNotEmpty()) View.VISIBLE else View.GONE
            inputEditText.post {
                isRestoring = false
                viewModel.onFocusChanged(inputEditText.hasFocus())
            }
        }

    }

    private fun initViews() {
        inputEditText = findViewById(R.id.inputEditText)
        clearButton = findViewById(R.id.clearIcon)
        recyclerView = findViewById(R.id.rvListTrack)
        recyclerViewHistory = findViewById(R.id.rvListTrackHistory)
        historyLinear = findViewById(R.id.linearHistory)
        emptyState = findViewById(R.id.emptyState)
        networkLostError = findViewById(R.id.networkLostError)
        updateErrorButtonSearch = findViewById(R.id.updateErrorButtonSearch)
        progressBar = findViewById(R.id.progressBar)
        clearHistoryButton = findViewById(R.id.cleerHistotyButtonSearch)
    }

    private fun setupToolbar() {
        findViewById<MaterialToolbar>(R.id.tool_bar_in_searchActivity).setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupAdapters() {
        cardMusicAdapter = CardMusicAdapter(emptyList())
        historyCardMusicAdapter = CardMusicAdapter(emptyList())
        recyclerView.adapter = cardMusicAdapter
        recyclerViewHistory.adapter = historyCardMusicAdapter
    }



    private fun observeViewModel() {
        viewModel.observeState().observe(this) { state ->
            render(state)
        }
    }

    private fun setupListeners() {
        clearButton.visibility = View.GONE

        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRestoring) return
                viewModel.onQueryChanged(s?.toString() ?: "")
                clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        clearButton.setOnClickListener {
            inputEditText.text.clear()
            viewModel.onClearClick()
            hideKeyboard()
        }

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onSearchAction()
                hideKeyboard()
                true
            } else false
        }

        inputEditText.setOnFocusChangeListener { _, hasFocus ->
            viewModel.onFocusChanged(hasFocus)
        }

        cardMusicAdapter.setOnTrackClickClickListener { track ->
            viewModel.onTrackClick(track)
            val intent = Intent(this, AudioPlayerActivity::class.java)
            intent.putExtra("track_from_Adapter", track)
            startActivity(intent)
        }

        historyCardMusicAdapter.setOnTrackClickClickListener { track ->
            viewModel.onTrackClick(track)
            val intent = Intent(this, AudioPlayerActivity::class.java)
            intent.putExtra("track_from_Adapter", track)
            startActivity(intent)
        }
        recyclerView.adapter = cardMusicAdapter
        recyclerViewHistory.adapter = historyCardMusicAdapter

        clearHistoryButton.setOnClickListener {
            viewModel.onClearHistoryClick()
        }

        updateErrorButtonSearch.setOnClickListener {
            viewModel.onRetryClick()
        }
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Idle -> {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
                emptyState.visibility = View.GONE
                networkLostError.visibility = View.GONE
                historyLinear.visibility = View.GONE
            }
            is SearchState.Loading -> {
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                emptyState.visibility = View.GONE
                networkLostError.visibility = View.GONE
                historyLinear.visibility = View.GONE
            }
            is SearchState.Content -> {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                emptyState.visibility = View.GONE
                networkLostError.visibility = View.GONE
                historyLinear.visibility = View.GONE
                cardMusicAdapter.listTrack = state.tracks
                cardMusicAdapter.notifyDataSetChanged()
            }
            is SearchState.History -> {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
                emptyState.visibility = View.GONE
                networkLostError.visibility = View.GONE
                historyLinear.visibility = View.VISIBLE
                historyCardMusicAdapter.listTrack = state.tracks
                historyCardMusicAdapter.notifyDataSetChanged()
            }
            is SearchState.Empty -> {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
                emptyState.visibility = View.VISIBLE
                networkLostError.visibility = View.GONE
                historyLinear.visibility = View.GONE
            }
            is SearchState.Error -> {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
                emptyState.visibility = View.GONE
                networkLostError.visibility = View.VISIBLE
                historyLinear.visibility = View.GONE
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(inputEditText.windowToken, 0)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, inputEditText.text.toString())
    }

    companion object {
        private const val SEARCH_TEXT = "SEARCH_TEXT_KEY"
    }
}

