package com.zhuravlev.stockobserverapp.ui.view_pager

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zhuravlev.stockobserverapp.R

class FragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val recyclerView = itemView.findViewById<RecyclerView>(R.id.list_recycler_view)
}