package com.zhuravlev.stockobserverapp.ui.fragment.fragment_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.storage.Storage

class StocksAdapter(list: MutableList<Stock>, favourite: Boolean = false) :
    RecyclerView.Adapter<StockViewHolder>() {
    private var mList = list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val context = holder.view.context
        if ((position % 2) == 0) {
            holder.view.background = ContextCompat.getDrawable(context, R.drawable.background_item)
        } else {
            holder.view.background =
                ContextCompat.getDrawable(context, R.drawable.background_white_item)
        }
        with(mList[position]) {
            holder.title.text = this.symbol
            holder.description.text = this.description
            val text = this.price + " ₽"
            holder.price.text = text
            try {
                if (this.changePrice[0] == '-') {
                    holder.changePrice.setTextColor(Color.RED)
                } else {
                    holder.changePrice.setTextColor(Color.GREEN)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            holder.changePrice.text = this.changePrice

            if (this.imageUrl.isNotEmpty()) {
                Picasso.get()
                    .load(this.imageUrl)
                    .placeholder(R.drawable.ic_factory)
                    .error(R.drawable.ic_factory)
                    .into(holder.image)
            }
            holder.star.drawable.setTint(ContextCompat.getColor(context, getColorId(this.star)))
            holder.star.setOnClickListener {
                this.star = !this.star
                holder.star.drawable.setTint(ContextCompat.getColor(context, getColorId(this.star)))
                Storage.instance!!.changeStock(this)
            }
            holder.view.setOnClickListener { }
        }

    }

    private fun getColorId(star: Boolean): Int {
        return if (star) {
            R.color.yellow
        } else {
            R.color.grey
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updatePrice(map: Map<String, Pair<String, String>>) {
        for (i in 0..mList.lastIndex) {
            if (map.containsKey(mList[i].symbol)) {
                val pair = map[mList[i].symbol]!!
                mList[i].price = pair.first
                mList[i].changePrice = String.format(
                    "%.2f",
                    (pair.first.toDouble() - pair.second.toDouble())
                )
                notifyItemChanged(i)
            }
        }
    }
}