package com.zhuravlev.stockobserverapp.ui.view_pager

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.model.Stock
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
//TODO
    }

    private fun initStocks(holder: FragmentViewHolder, item: Fragment) {
        Storage.instance?.getStocks({ it ->
            holder.adapter = StocksAdapter(it)
            holder.recyclerView.adapter = holder.adapter
            Storage.instance?.getCurrentPrices({ map ->
                holder.adapter.updatePrice(map)
            }, {
                Toast.makeText(item.context, it.message, Toast.LENGTH_SHORT).show()
            })
        }, { list: List<Stock>, throwable: Throwable ->
            holder.adapter = StocksAdapter(list)
            holder.recyclerView.adapter = holder.adapter
            Toast.makeText(item.context, throwable.message, Toast.LENGTH_SHORT).show()
        })
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}