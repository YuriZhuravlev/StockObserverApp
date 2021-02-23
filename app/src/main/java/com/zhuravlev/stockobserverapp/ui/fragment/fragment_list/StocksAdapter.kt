package com.zhuravlev.stockobserverapp.ui.fragment.fragment_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.model.Stock

class StocksAdapter(list: List<Stock>) : RecyclerView.Adapter<StockViewHolder>() {
    private val mList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        with(mList[position]) {
            holder.title.text = this.title
            holder.description.text = this.description
            holder.price.text = this.price
            holder.changePrice.text = this.changePrice
            if (this.star) {
                holder.star.setColorFilter(R.color.gold)
            }
            holder.star.setOnClickListener {
                if (this.star) {
                    this.star = false
                    holder.star.setColorFilter(R.color.white)
                } else {
                    this.star = true
                    holder.star.setColorFilter(R.color.gold)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }
}