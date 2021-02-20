package com.zhuravlev.stockobserverapp.storage

import com.zhuravlev.stockobserverapp.model.Profile
import com.zhuravlev.stockobserverapp.model.ResponseSearchSymbol
import com.zhuravlev.stockobserverapp.model.ResponseSearchSymbolsFromExchange
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
        onSuccess: (ResponseSearchSymbolsFromExchange) -> Unit,
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