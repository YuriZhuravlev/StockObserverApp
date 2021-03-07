package com.zhuravlev.stockobserverapp.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.ui.view_pager.FragmentAdapter
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mViewPager: ViewPager2
    private lateinit var mTab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mToolbar = findViewById(R.id.main_toolbar)
        mViewPager = findViewById(R.id.main_view_pager)
        mTab = findViewById(R.id.main_tab)

        mViewPager.adapter = FragmentAdapter(listOf(Fragment(), Fragment()))
        TabLayoutMediator(mTab, mViewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Stocks"
            } else {
                tab.text = "Favourites"
            }
        }.attach()
    }

    override fun setSupportActionBar(toolbar: androidx.appcompat.widget.Toolbar?) {
        super.setSupportActionBar(mToolbar)
    }
}