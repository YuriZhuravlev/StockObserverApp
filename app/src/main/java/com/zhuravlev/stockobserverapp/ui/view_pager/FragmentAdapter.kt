package com.zhuravlev.stockobserverapp.ui.view_pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.zhuravlev.stockobserverapp.R

class FragmentAdapter(list: List<Fragment>) : RecyclerView.Adapter<FragmentViewHolder>() {
    private val mList = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentViewHolder {
        return FragmentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int) {
        // TODO потому что по-идее не будет работать
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}