package com.zhuravlev.stockobserverapp.ui.fragment.fragment_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
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
        val context = holder.view.context
        if ((position % 2) == 0) {
            holder.view.background = ContextCompat.getDrawable(context, R.drawable.background_item)
        }
        with(mList[position]) {
            holder.title.text = this.title
            holder.description.text = this.description
            holder.price.text = this.price
            holder.changePrice.text = this.changePrice

            if (this.imageUrl.isNotEmpty()) {
                Picasso.get()
                    .load(this.imageUrl)
                    .resize(52, 52)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.image)
            }
            holder.star.drawable.setTint(ContextCompat.getColor(context, getColorId(this.star)))
            holder.star.setOnClickListener {
                this.star = !this.star
                holder.star.drawable.setTint(ContextCompat.getColor(context, getColorId(this.star)))
            }
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
}