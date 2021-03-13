package com.zhuravlev.stockobserverapp.ui.activity

import com.zhuravlev.stockobserverapp.model.Stock
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.SingleState

interface MainView : MvpView {
    @AddToEndSingle
    fun initStockLists()

    @AddToEndSingle
    fun initStock(stock: Stock)

    @SingleState
    fun showError(message: String)

    @SingleState
    fun hideError()
}