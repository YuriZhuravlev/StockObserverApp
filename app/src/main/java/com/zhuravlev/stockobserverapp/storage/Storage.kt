package com.zhuravlev.stockobserverapp.storage

import com.zhuravlev.stockobserverapp.model.Profile
import com.zhuravlev.stockobserverapp.model.ResponseSearchSymbol
import com.zhuravlev.stockobserverapp.model.ResponseSearchSymbolsFromExchange
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.storage.net.TOKEN
import com.zhuravlev.stockobserverapp.storage.net.getApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class Storage {
    fun searchSymbol(
        symbol: String,
        onSuccess: (ResponseSearchSymbol) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        getApiService().getSymbolLookup(symbol, TOKEN).request(onSuccess, onError)
    }

    fun getStocksFromExchange(
        exchange: String,
        onSuccess: (List<ResponseSearchSymbolsFromExchange>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        getApiService().getStockSymbolFromExchange(exchange, TOKEN).request(onSuccess, onError)
    }

    fun getProfile(
        symbol: String, onSuccess: (Profile) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        getApiService().getCompany(symbol, TOKEN).request(onSuccess, onError)
    }

    fun getStocks(
        onSuccess: (List<Stock>) -> Unit,
        onError: (Throwable) -> Unit
    ): MutableList<Stock> {
        val list = mutableListOf<Stock>()
        getStocksFromExchange("V", {
            it.forEach { item ->
                list.add(Stock(item.symbol!!, "", item.description!!, false, "", ""))
            }
            list.forEach { company ->
                getProfile(company.title, { profile ->
                    company.imageUrl = profile.logo.toString()
                }, { onError(it) })
            }
            onSuccess(list)
        }, onError = { onError(it) })
        return list
    }
}

private fun <T> Single<T>.request(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            //TODO("Добавить обёртку для сохранения данных в db")
            onSuccess(it)
        }, {
            //TODO("Добавить обёртку для данных из db")
            onError(it)
        })
}