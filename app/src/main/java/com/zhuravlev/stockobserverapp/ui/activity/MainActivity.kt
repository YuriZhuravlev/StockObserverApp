package com.zhuravlev.stockobserverapp.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.storage.ISearch
import com.zhuravlev.stockobserverapp.storage.Search
import com.zhuravlev.stockobserverapp.storage.Storage
import com.zhuravlev.stockobserverapp.ui.Refreshable
import com.zhuravlev.stockobserverapp.ui.view_pager.FragmentAdapter
import io.reactivex.rxjava3.subjects.PublishSubject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), Refreshable {
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mSearchView: SearchView
    private lateinit var mViewPager: ViewPager2
    private lateinit var mTab: TabLayout
    private lateinit var mTextInfo: TextView
    private lateinit var mRefreshButton: ExtendedFloatingActionButton
    private lateinit var mQueryListener: QueryListener


    private class QueryListener(val searchView: SearchView) : SearchView.OnQueryTextListener,
        ISearch {
        override val query: PublishSubject<String> = PublishSubject.create()

        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            query.onNext(searchView.query.toString())
            return true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mToolbar = findViewById(R.id.main_toolbar)

        initSearch()



        mTextInfo = findViewById(R.id.main_text_info)
        mRefreshButton = findViewById(R.id.main_refresh_btn)
        mRefreshButton.setOnClickListener { Storage.instance!!.updatePrices() }
        Storage.instance!!.setRefreshable(this)

        initTab()
    }

    private fun initSearch() {
        mSearchView = findViewById(R.id.toolbar_search_view)
        mQueryListener = QueryListener(mSearchView)
        mSearchView.setOnQueryTextListener(mQueryListener)
        Search.instance.setSearchView(mQueryListener)
    }

    private fun initTab() {
        mViewPager = findViewById(R.id.main_view_pager)
        mTab = findViewById(R.id.main_tab)
        mViewPager.adapter = FragmentAdapter(listOf(Fragment(), Fragment()))
        TabLayoutMediator(mTab, mViewPager) { tab, position ->
            if (position == 0) {
                tab.setCustomView(R.layout.tab_title)
                tab.text = getString(R.string.stocks)
            } else {
                tab.setCustomView(R.layout.tab_title)
                tab.text = getString(R.string.favourites)
            }
        }.attach()
    }

    override fun setSupportActionBar(toolbar: androidx.appcompat.widget.Toolbar?) {
        super.setSupportActionBar(mToolbar)
    }

    override fun startRefresh() {
        mRefreshButton.visibility = View.GONE
        mTextInfo.visibility = View.VISIBLE
        mTextInfo.background.setTint(Color.WHITE)
        mTextInfo.text = getString(R.string.refreshing)
    }

    override fun showRefreshButton() {
        mRefreshButton.visibility = View.VISIBLE
    }

    override fun endRefresh() {
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val text: String = getString(R.string.update_time) + " " + timeFormat.format(Date())
        mTextInfo.background.setTint(ContextCompat.getColor(this, R.color.dark_green))
        mTextInfo.text = text
    }

    override fun hideRefresh() {
        mTextInfo.visibility = View.GONE
    }
}