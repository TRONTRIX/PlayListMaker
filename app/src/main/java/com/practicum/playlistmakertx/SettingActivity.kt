package com.practicum.playlistmakertx

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)

        val toolBarBack = findViewById<MaterialToolbar>(R.id.tool_bar)
        toolBarBack.setNavigationOnClickListener{
            finish()
        }

        val userSettingButtonActivity = findViewById<FrameLayout>(R.id.user_agreement)
        userSettingButtonActivity.setOnClickListener {
            val url = Uri.parse("https://yandex.ru/legal/practicum_offer/")
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }

        val userSupportBottonActivity = findViewById<FrameLayout>(R.id.support_botton)
        userSupportBottonActivity.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("thedrof3cool@mail.ru"))
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


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}