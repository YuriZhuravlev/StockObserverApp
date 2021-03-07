package com.zhuravlev.stockobserverapp.ui.view_pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.model.moex.converters.parseResponsePriceAllStocksByDate
import com.zhuravlev.stockobserverapp.storage.Storage
import com.zhuravlev.stockobserverapp.ui.fragment.fragment_list.StocksAdapter

private const val STOCKS = 0
private const val FAVOURITE = 1

class FragmentAdapter(list: List<Fragment>) : RecyclerView.Adapter<FragmentViewHolder>() {
    private val mList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentViewHolder {
        return FragmentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int) {
        when (getItemViewType(position)) {
            STOCKS -> {
                initStocks(holder, mList[position])
            }
            FAVOURITE -> {
                initFavourite(holder, mList[position])
            }
            else -> {
            }
        }
    }

    private fun initFavourite(holder: FragmentViewHolder, item: Fragment) {
        val list = listOf<Stock>(
            Stock("AAAA", "", "AAAA technology corp", true, "$9999.24", "+4.84%"),
            Stock("AAAB", "", "AAAB agronom kolhoz", true, "₽124.06", "+0.5")
        )
        holder.recyclerView.adapter = StocksAdapter(list)
    }

    private fun initStocks(holder: FragmentViewHolder, item: Fragment) {
        Storage().getStocks({ it ->
            holder.adapter = StocksAdapter(it)
            holder.recyclerView.adapter = holder.adapter
            Storage().getCurrentPrices({ priceList ->
                parseResponsePriceAllStocksByDate(priceList).subscribe({ map ->
                    holder.adapter.updatePrice(map)
                }, {
                    it.printStackTrace()
                })
            }, {
                it.printStackTrace()
            })
        }, {
            // todo
            it.printStackTrace()
        })
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}