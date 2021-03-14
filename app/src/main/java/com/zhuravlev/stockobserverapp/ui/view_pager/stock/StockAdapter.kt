package com.zhuravlev.stockobserverapp.ui.view_pager.stock

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.storage.Storage
import com.zhuravlev.stockobserverapp.ui.view.ChartMarkerView
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
        holder.chart.setNoDataText(holder.chart.context.getString(R.string.loading))
        val calendar = GregorianCalendar.getInstance()
        calendar.time = Date()
        calendar.roll(Calendar.YEAR, -1)
        Storage.instance?.getCandle(stock, calendar.time) { priceChart ->
            val entriesHigh = mutableListOf<Entry>()
            val entriesLow = mutableListOf<Entry>()
            var d = 0f
            priceChart.list.forEach {
                entriesHigh.add(Entry(d, it.highPrice.toFloat()))
                entriesLow.add(Entry(d, it.lowPrice.toFloat()))
                d += 1f
            }
            val lineDataSetHigh = LineDataSet(entriesHigh, "High price")
            val lineDataSetLow = LineDataSet(entriesLow, "Low price")

            lineDataSetHigh.color = Color.RED
            lineDataSetHigh.lineWidth = 1f
            lineDataSetHigh.setCircleColor(Color.RED)

            lineDataSetLow.color = Color.GREEN
            lineDataSetLow.lineWidth = 1f
            lineDataSetHigh.setCircleColor(Color.GREEN)

            val iLineDataSets: MutableList<ILineDataSet> =
                mutableListOf(lineDataSetHigh, lineDataSetLow)
            val lineData = LineData(iLineDataSets)


            lineDataSetHigh.color = Color.RED
            lineDataSetHigh.lineWidth = 1f
            lineDataSetHigh.setDrawCircles(false)
            lineDataSetHigh.axisDependency = YAxis.AxisDependency.LEFT
            lineDataSetHigh.setDrawFilled(true)
            lineDataSetHigh.fillColor = Color.DKGRAY
            lineDataSetHigh.setDrawHorizontalHighlightIndicator(false)
            lineDataSetHigh.highLightColor = Color.BLACK

            lineDataSetLow.color = Color.GREEN
            lineDataSetLow.lineWidth = 1f
            lineDataSetLow.setDrawCircles(false)
            lineDataSetLow.axisDependency = YAxis.AxisDependency.LEFT
            lineDataSetLow.setDrawFilled(true)
            lineDataSetLow.fillAlpha = 255
            lineDataSetLow.fillColor = Color.WHITE
            lineDataSetLow.setDrawHorizontalHighlightIndicator(false)
            lineDataSetLow.highLightColor = Color.BLACK

            with(holder.chart) {
                this.marker = ChartMarkerView(this.context, priceChart)
                this.setMaxVisibleValueCount(24)
                this.animateX(400)

                this.description.isEnabled = false
                this.legend.isEnabled = false
                this.setPinchZoom(false)
                this.setTouchEnabled(true)
                this.isDoubleTapToZoomEnabled = false
                this.setDrawGridBackground(false)
                this.xAxis.isEnabled = false

                this.axisLeft.isEnabled = false

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