package com.zhuravlev.stockobserverapp.ui.view_pager.stock

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.storage.Storage
import java.text.SimpleDateFormat
import java.util.*

private const val CHART = 0

class StockAdapter(val stock: Stock) : RecyclerView.Adapter<StockViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_chart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CHART -> {
                initChart(holder)
            }
        }
    }

    private fun initChart(holder: StockViewHolder) {
        val calendar = GregorianCalendar.getInstance()
        calendar.time = Date()
        calendar.roll(Calendar.YEAR, -1)
        Storage.instance?.getCandle(stock, calendar.time) { priceChart ->
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val entriesHigh = mutableListOf<Entry>()
            val entriesLow = mutableListOf<Entry>()
            var d = 0f
            priceChart.list.forEach {
                entriesHigh.add(Entry(d, it.highPrice.toFloat(), dateFormat.format(it.date)))
                entriesLow.add(Entry(d, it.lowPrice.toFloat(), dateFormat.format(it.date)))
                d += 1f
            }
            val lineDataSetHigh = LineDataSet(entriesHigh, "High price")
            val lineDataSetLow = LineDataSet(entriesLow, "Low price")

            lineDataSetHigh.color = Color.RED
            lineDataSetHigh.lineWidth = 2f
            lineDataSetHigh.circleRadius = 1f
            lineDataSetHigh.setCircleColor(Color.RED)

            lineDataSetLow.color = Color.GREEN
            lineDataSetLow.lineWidth = 2f
            lineDataSetHigh.circleRadius = 1f
            lineDataSetHigh.setCircleColor(Color.GREEN)

            val iLineDataSets: MutableList<ILineDataSet> =
                mutableListOf(lineDataSetHigh, lineDataSetLow)
            val lineData = LineData(iLineDataSets)


            lineDataSetHigh.setDrawCircles(false)
            lineDataSetHigh.axisDependency = YAxis.AxisDependency.LEFT

            lineDataSetLow.setDrawCircles(false)
            lineDataSetLow.axisDependency = YAxis.AxisDependency.LEFT

            with(holder.chart) {
                this.setMaxVisibleValueCount(60)

                this.setPinchZoom(false)
                this.setDrawGridBackground(false)
                val xAxis = this.xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)

                val yAxis = this.axisLeft
                yAxis.setLabelCount(7, false)
                yAxis.setDrawGridLines(false)
                yAxis.setDrawAxisLine(false)

                this.axisRight.isEnabled = false

                this.data = lineData
                this.invalidate()
            }
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return CHART
    }
}