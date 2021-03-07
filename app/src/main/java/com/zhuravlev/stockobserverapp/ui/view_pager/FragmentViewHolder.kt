package com.zhuravlev.stockobserverapp.ui.view_pager

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.ui.fragment.fragment_list.StocksAdapter

class FragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val recyclerView = itemView.findViewById<RecyclerView>(R.id.list_recycler_view)
    lateinit var adapter: StocksAdapter
}