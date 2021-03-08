package com.zhuravlev.stockobserverapp.ui.fragment.fragment_list

import com.zhuravlev.stockobserverapp.model.Stock

class FavouritesAdapter : StocksAdapter() {
    override fun addList(list: List<Stock>) {
        var i = 0
        var first = 0
        while (i < mList.size) {
            if (!list.contains(mList[i])) {
                if (first == 0) {
                    first = i
                }
                mList.removeAt(i)
                notifyItemRemoved(i)
            } else {
                i++
            }
        }
        notifyItemRangeChanged(first, mList.lastIndex)
        super.addList(list)
    }
}