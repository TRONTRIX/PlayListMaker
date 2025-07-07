package com.practicum.playlistmakertx

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

class SearchActivity : AppCompatActivity() {
    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT_KEY"
        const val DEFAULT_TEXT = ""
    }
    private var savedEditTextSearch: String = DEFAULT_TEXT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)


        if (savedInstanceState != null) {
            savedEditTextSearch = savedInstanceState.getString(SEARCH_TEXT, DEFAULT_TEXT)
        }

        val toolBarBack = findViewById<MaterialToolbar>(R.id.tool_bar_in_searchActivity)
        toolBarBack.setNavigationOnClickListener{
            finish()
        }


        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)

        clearButton.setOnClickListener {
            inputEditText.setText("")
            savedEditTextSearch = ""
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


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, savedEditTextSearch)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedEditTextSearch = savedInstanceState.getString(SEARCH_TEXT, DEFAULT_TEXT)
    }
    override fun onResume() {
        super.onResume()
        findViewById<EditText>(R.id.inputEditText).setText(savedEditTextSearch)
    }
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}