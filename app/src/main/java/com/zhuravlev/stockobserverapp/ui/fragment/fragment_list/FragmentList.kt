package com.zhuravlev.stockobserverapp.ui.fragment.fragment_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zhuravlev.stockobserverapp.R
import moxy.MvpAppCompatFragment

open class FragmentList : MvpAppCompatFragment() {
    protected open lateinit var mRecyclerView: RecyclerView
    protected open lateinit var mAdapter: StocksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_list, container, false)
        mRecyclerView = v.findViewById(R.id.list_recycler_view)
        return v
    }

    override fun onResume() {
        super.onResume()
        initAdapter() {
            initRecyclerView()
        }
    }

    /**
     * Инициализация адаптера (по умолчанию пустой)
     */
    protected open fun initAdapter(initRecyclerView: () -> Unit) {
        mAdapter = StocksAdapter(listOf())
    }

    /**
     * Инициализация RecyclerView (по умолчанию установка mAdapter)
     */
    protected open fun initRecyclerView() {
        mRecyclerView.adapter = mAdapter
    }
}