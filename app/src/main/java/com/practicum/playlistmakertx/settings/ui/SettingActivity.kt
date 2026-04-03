package com.practicum.playlistmakertx.settings.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial

import com.practicum.playlistmakertx.R
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingActivity : AppCompatActivity() {

    private val viewModel: SettingViewModel by viewModel()
    private lateinit var themeSwitcher: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)

        initViews()
        setupToolbar()

        observeViewModel()
        setupListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        themeSwitcher = findViewById(R.id.themeSwitcher)
    }

    private fun setupToolbar() {
        val toolBarBack = findViewById<MaterialToolbar>(R.id.tool_bar)
        toolBarBack.setNavigationOnClickListener {
            finish()
        }
    }



    private fun observeViewModel() {
        viewModel.observeThemeState().observe(this) { isDark ->
            if (themeSwitcher.isChecked != isDark) {
                themeSwitcher.isChecked = isDark
            }
        }
    }

    private fun setupListeners() {
        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.onThemeToggled(checked)
        }
        val userSettingButtonActivity = findViewById<FrameLayout>(R.id.user_agreement)
        userSettingButtonActivity.setOnClickListener {
            val url = Uri.parse(getString(R.string.practicumOffer))
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }

        val userSupportBottonActivity = findViewById<FrameLayout>(R.id.support_botton)
        userSupportBottonActivity.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.developerEmail)))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subjectEmail))
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.textSupportEmail))
            startActivity(intent)
        }

        val userShareButtonActivity = findViewById<FrameLayout>(R.id.share_button)
        userShareButtonActivity.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.UrlAndroidDeveloper))
            startActivity(intent)
        }
    }
}