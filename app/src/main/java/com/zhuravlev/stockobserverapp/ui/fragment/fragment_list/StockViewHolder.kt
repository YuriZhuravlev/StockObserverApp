package com.zhuravlev.stockobserverapp.ui.fragment.fragment_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhuravlev.stockobserverapp.R

class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.findViewById<TextView>(R.id.item_stock_title)
    val description = itemView.findViewById<TextView>(R.id.item_stock_description)
    val image = itemView.findViewById<ImageView>(R.id.item_stock_image)
    val price = itemView.findViewById<TextView>(R.id.item_stock_price)
    val changePrice = itemView.findViewById<TextView>(R.id.item_stock_change_price)
    val star = itemView.findViewById<ImageView>(R.id.item_stock_star)
}