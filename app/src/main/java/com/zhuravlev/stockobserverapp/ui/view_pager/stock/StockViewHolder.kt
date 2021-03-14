package com.zhuravlev.stockobserverapp.ui.view_pager.stock

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.zhuravlev.stockobserverapp.R

class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val chart = itemView.findViewById<LineChart>(R.id.chart_chart)
}