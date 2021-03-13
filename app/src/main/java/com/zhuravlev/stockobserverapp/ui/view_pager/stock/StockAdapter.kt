package com.zhuravlev.stockobserverapp.ui.view_pager.stock

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.model.Stock

private const val CHART = 0

class StockAdapter(val stock: Stock) : RecyclerView.Adapter<StockViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CHART -> {
                initChart()
            }
        }
    }

    private fun initChart() {

    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return CHART
    }
}