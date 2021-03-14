package com.zhuravlev.stockobserverapp.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.storage.ISearch
import com.zhuravlev.stockobserverapp.storage.Search
import com.zhuravlev.stockobserverapp.ui.view_pager.stock.StockAdapter
import com.zhuravlev.stockobserverapp.ui.view_pager.stocks.FragmentAdapter
import io.reactivex.rxjava3.subjects.PublishSubject
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import java.util.*

class MainActivity : MvpAppCompatActivity(), MainView {
    private lateinit var mMainToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mChartToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mSearchView: SearchView
    private lateinit var mViewPager: ViewPager2
    private lateinit var mTab: TabLayout
    private lateinit var mTabLayoutMediatorStocks: TabLayoutMediator
    private lateinit var mTabLayoutMediatorStock: TabLayoutMediator
    private lateinit var mTextInfo: TextView
    private lateinit var mTitle: TextView
    private lateinit var mDescription: TextView
    private lateinit var mStar: ImageView
    private lateinit var mBackspace: ImageView
    private lateinit var mQueryListener: QueryListener
    private val mFragmentAdapter = FragmentAdapter()
    private val ruLocale: Boolean = Locale.getDefault().language.equals(Locale("ru").language)

    @InjectPresenter
    lateinit var mPresenter: MainPresenter


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
        mMainToolbar = findViewById(R.id.main_toolbar)
        mChartToolbar = findViewById(R.id.chart_toolbar)
        mViewPager = findViewById(R.id.main_view_pager)
        mTab = findViewById(R.id.main_tab)
        mTextInfo = findViewById(R.id.main_text_info)
        mSearchView = findViewById(R.id.toolbar_search_view)
        mTitle = findViewById(R.id.toolbar_chart_title)
        mDescription = findViewById(R.id.toolbar_chart_description)
        mStar = findViewById(R.id.toolbar_chart_star)
        mBackspace = findViewById(R.id.toolbar_chart_backspace)

        mStar.setOnClickListener {
            mStar.isSelected = !mStar.isSelected
            mPresenter.clickStar()
        }
        mBackspace.setOnClickListener {
            mPresenter.setStockList()
        }

        mTabLayoutMediatorStocks = TabLayoutMediator(mTab, mViewPager) { tab, position ->
            if (position == 0) {
                tab.setCustomView(R.layout.tab_title)
                tab.text = getString(R.string.stocks)
            } else {
                tab.setCustomView(R.layout.tab_title)
                tab.text = getString(R.string.favourites)
            }
        }
        mTabLayoutMediatorStock = TabLayoutMediator(mTab, mViewPager) { tab, position ->
            if (position == 0) {
                tab.setCustomView(R.layout.tab_title)
                tab.text = getString(R.string.chart)
            }
        }

        initSearch()
    }

    private fun initSearch() {
        mQueryListener = QueryListener(mSearchView)
        mSearchView.setOnQueryTextListener(mQueryListener)
        Search.instance.setSearchView(mQueryListener)
    }

    override fun onBackPressed() {
        if (mPresenter.stocksScreen) {
            super.onBackPressed()
        } else {
            mPresenter.setStockList()
        }
    }

    override fun showError(message: String) {
        mTextInfo.text = getString(R.string.connection_error)
        mTextInfo.visibility = View.VISIBLE
    }

    override fun hideError() {
        mTextInfo.visibility = View.GONE
    }

    override fun initStockLists(item: Int) {
        if (mTabLayoutMediatorStock.isAttached) {
            mTabLayoutMediatorStock.detach()
        }
        mChartToolbar.visibility = View.GONE
        mMainToolbar.visibility = View.VISIBLE
        mViewPager.adapter = mFragmentAdapter
        mViewPager.currentItem = item
        if (!mTabLayoutMediatorStocks.isAttached) {
            mTabLayoutMediatorStocks.attach()
        }
    }

    override fun initStock(stock: Stock) {
        if (mTabLayoutMediatorStocks.isAttached) {
            mPresenter.stocksItem = mViewPager.currentItem
            mTabLayoutMediatorStocks.detach()
        }
        mTitle.text = stock.symbol
        mDescription.text = if (ruLocale) {
            stock.description
        } else {
            stock.enDescription
        }
        mStar.isSelected = stock.star
        mMainToolbar.visibility = View.GONE
        mChartToolbar.visibility = View.VISIBLE
        mViewPager.adapter = StockAdapter(stock)
        if (!mTabLayoutMediatorStock.isAttached) {
            mTabLayoutMediatorStock.attach()
        }
    }
}