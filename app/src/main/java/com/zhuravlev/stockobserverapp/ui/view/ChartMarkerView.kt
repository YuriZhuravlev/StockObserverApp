package com.zhuravlev.stockobserverapp.ui.view

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.model.PriceChart
import java.text.SimpleDateFormat
import java.util.*


class ChartMarkerView(context: Context?, priceChart: PriceChart) :
    MarkerView(context, R.layout.view_marker) {
    private val list = priceChart.list.toList()
    private val mTextPrice: TextView = findViewById(R.id.marker_price)
    private val mTextVolume: TextView = findViewById(R.id.marker_volume)
    private val mTextDate: TextView = findViewById(R.id.marker_date)

    override fun refreshContent(e: Entry, highlight: Highlight) {
        val item = list[e.x.toInt()]
        var text = "${item.lowPrice}₽ - ${item.highPrice}₽"
        mTextPrice.text = text
        text = "${context.getString(R.string.volume)} ${item.volume}"
        mTextVolume.text = text
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        mTextDate.text = dateFormat.format(item.date)

        super.refreshContent(e, highlight)
    }

    private var mOffset: MPPointF? = null
    override fun getOffset(): MPPointF {
        if (mOffset == null) {
            mOffset = MPPointF((-(width / 2)).toFloat(), (-height - 150).toFloat())
        }
        return mOffset!!
    }
}