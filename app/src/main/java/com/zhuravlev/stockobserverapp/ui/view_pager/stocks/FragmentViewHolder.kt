package com.zhuravlev.stockobserverapp.ui.view_pager.stocks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.ui.fragment.list.StocksAdapter

class FragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val recyclerView = itemView.findViewById<RecyclerView>(R.id.list_recycler_view)
    val swipeRefreshLayout = itemView.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
    lateinit var adapter: StocksAdapter
}