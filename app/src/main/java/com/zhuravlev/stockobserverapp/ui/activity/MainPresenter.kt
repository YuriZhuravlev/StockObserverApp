package com.zhuravlev.stockobserverapp.ui.activity

import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.storage.Storage
import com.zhuravlev.stockobserverapp.ui.Shower
import moxy.MvpPresenter

class MainPresenter : MvpPresenter<MainView>(), Shower {
    var stocksScreen = true
    var stock: Stock? = null

    override fun attachView(view: MainView?) {
        super.attachView(view)
        instance = this
        Storage.instance?.setShower(this)
        if (stocksScreen) {
            viewState.initStockLists()
        } else {
            viewState.initStock(stock!!)
        }
    }

    override fun detachView(view: MainView?) {
        Storage.instance?.setShower(null)
        instance = null
        super.detachView(view)
    }

    companion object {
        var instance: MainPresenter? = null
    }

    override fun showError(message: String) {
        viewState.showError(message)
    }

    override fun hideError() {
        viewState.hideError()
    }

    fun setStockList() {
        stock = null
        stocksScreen = true
        viewState.initStockLists()
    }

    fun setChart(stock: Stock) {
        this.stock = stock
        stocksScreen = false
        viewState.initStock(stock)
    }

    fun clickStar() {
        val s = stock
        if (s != null) {
            s.star = !s.star
            Storage.instance?.changeStock(s)
        }
    }
}