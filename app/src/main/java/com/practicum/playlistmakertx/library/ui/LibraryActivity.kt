package com.practicum.playlistmakertx.library.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.practicum.playlistmakertx.R
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LibraryActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        setupToolbar()
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        viewPager.adapter = LibraryViewPagerAdapter(supportFragmentManager, lifecycle)

        tabMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
             when (position) {
                0 -> tab.text = getString(R.string.favoriteTracks)
                1 -> tab.text = getString(R.string.myPlayList)
            }
        }
        tabMediator.attach()
        val tabStrip = tabLayout.getChildAt(0) as ViewGroup

        for (i in 0 until tabStrip.childCount) {
            val tab = tabStrip.getChildAt(i)

            val params = tab.layoutParams as ViewGroup.MarginLayoutParams
            params.marginStart = 16.dp()
            params.marginEnd = 16.dp()

            tab.layoutParams = params
            tab.requestLayout()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::tabMediator.isInitialized) {
            tabMediator.detach()
        }
    }
    private fun setupToolbar() {
        val toolBarBack = findViewById<MaterialToolbar>(R.id.tool_bar_in_libraryActivity)
        toolBarBack.setNavigationOnClickListener {
            finish()
        }
    }
    fun Int.dp(): Int =
        (this * resources.displayMetrics.density).toInt()
}

