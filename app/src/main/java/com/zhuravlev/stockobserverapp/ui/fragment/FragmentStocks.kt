package com.zhuravlev.stockobserverapp.ui.fragment

import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.ui.fragment.fragment_list.FragmentList
import com.zhuravlev.stockobserverapp.ui.fragment.fragment_list.StocksAdapter

class FragmentStocks : FragmentList() {
    override fun initAdapter(initRecyclerView: () -> Unit) {
        // TODO обращение к Storage
        // заглушка
        val list = listOf<Stock>(
            Stock("ZEON", "", "ZEON madice corp", false, "$9999.24", "+4.84%"),
            Stock("PANT", "", "Panteon neftegaz", false, "₽124.06", "+0.5"),
            Stock("AAAA", "", "AAAA technology corp", true, "$9999.24", "+4.84%"),
            Stock("AAAB", "", "AAAB agronom kolhoz", true, "₽124.06", "+0.5"),
            Stock("AAAC", "", "AAAC technology corp", false, "$9999.24", "+4.84%"),
            Stock("AAAD", "", "AAAD agronom kolhoz", false, "₽124.06", "+0.5")
        )
        mAdapter = StocksAdapter(list)
    }
}