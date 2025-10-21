package com.practicum.playlistmakertx

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.practicum.playlistmakertx.searchActivityAPI.ITunesSearchAPI
import com.practicum.playlistmakertx.searchActivityAPI.TrackResponse
import com.practicum.playlistmakertx.searchActivityRecyclerView.CardMusicAdapter
import com.practicum.playlistmakertx.searchActivityRecyclerView.Track
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class SearchActivity : AppCompatActivity() {
    private var savedEditTextSearch: String = DEFAULT_TEXT

    private lateinit var cardMusicAdapter: CardMusicAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: LinearLayout
    private lateinit var networkLostError: LinearLayout
    private lateinit var updateErrorButtonSearch: Button


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val iTunseService = retrofit.create(ITunesSearchAPI::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        initViews() // !!!!!!!!


        if (savedInstanceState != null) {
            savedEditTextSearch = savedInstanceState.getString(SEARCH_TEXT, DEFAULT_TEXT)
        }

        val toolBarBack = findViewById<MaterialToolbar>(R.id.tool_bar_in_searchActivity)
        toolBarBack.setNavigationOnClickListener {
            finish()
        }


        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val saveEditTextOnCreate = findViewById<EditText>(R.id.inputEditText)

        clearButton.setOnClickListener {
            inputEditText.setText("")
            savedEditTextSearch = ""
            hideKeyboard(inputEditText)
            cardMusicAdapter.listTrack = emptyList()
            cardMusicAdapter.notifyDataSetChanged()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                savedEditTextSearch = s.toString() ?: ""
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)
        saveEditTextOnCreate.setText(savedEditTextSearch)

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val searchText = inputEditText.text.toString().trim()
                if (searchText.isNotEmpty()) {
                    activitySearch(searchText)
                    hideKeyboard(inputEditText)
                } else {
                    cardMusicAdapter.listTrack = emptyList()
                    cardMusicAdapter.notifyDataSetChanged()
                }

                true
            }
            false
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.rvListTrack)
        emptyState = findViewById(R.id.emptyState)
        networkLostError = findViewById(R.id.networkLostError)
        updateErrorButtonSearch = findViewById(R.id.updateErrorButtonSearch)


        cardMusicAdapter = CardMusicAdapter(emptyList())
        recyclerView.adapter = cardMusicAdapter

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, savedEditTextSearch)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedEditTextSearch = savedInstanceState.getString(SEARCH_TEXT, DEFAULT_TEXT)
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun hideKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    private fun activitySearch(searchText: String) {
        iTunseService.search(searchText)
            .enqueue(object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse?>,
                    response: Response<TrackResponse?>
                ) {
                    if (response.isSuccessful) {
                        val tracks = response.body()?.results ?: emptyList()
                        cardMusicAdapter.listTrack = tracks
                        cardMusicAdapter.notifyDataSetChanged()

                        if (tracks.isEmpty()) {
                            emptyStateVisible()
                        } else {
                           recyclerViewVisible()
                        }
                    } else {
                       networkLostErrorVisible(searchText)
                    }
                }

                override fun onFailure(
                    call: Call<TrackResponse?>,
                    t: Throwable
                ) {
                    networkLostErrorVisible(searchText)
                }

            })
    }
    private fun  emptyStateVisible() {
        emptyState.visibility = View.VISIBLE
        networkLostError.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }
    private fun  recyclerViewVisible() {
        emptyState.visibility = View.GONE
        networkLostError.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun  networkLostErrorVisible(searchText: String) {
        emptyState.visibility = View.GONE
        networkLostError.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        updateErrorButtonSearch.setOnClickListener {
            activitySearch(searchText)}
    }


    companion object {
        private const val SEARCH_TEXT = "SEARCH_TEXT_KEY"
        private const val DEFAULT_TEXT = ""
        private const val BASE_URL = "https://itunes.apple.com"//test
    }
}

