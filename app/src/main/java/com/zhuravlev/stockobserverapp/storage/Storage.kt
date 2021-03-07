package com.zhuravlev.stockobserverapp.storage

import com.zhuravlev.stockobserverapp.model.Profile
import com.zhuravlev.stockobserverapp.model.ResponseSearchSymbol
import com.zhuravlev.stockobserverapp.model.ResponseSearchSymbolsFromExchange
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.model.moex.ResponsePriceAllStocksByDate
import com.zhuravlev.stockobserverapp.model.moex.converters.parseSecurities
import com.zhuravlev.stockobserverapp.storage.net.TOKEN
import com.zhuravlev.stockobserverapp.storage.net.getFinnhubApiService
import com.zhuravlev.stockobserverapp.storage.net.getMoexApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class Storage {
    fun searchSymbol(
        symbol: String,
        onSuccess: (ResponseSearchSymbol) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        getFinnhubApiService().getSymbolLookup(symbol, TOKEN).request(onSuccess, onError)
    }

    fun getStocksFromExchange(
        exchange: String,
        onSuccess: (List<ResponseSearchSymbolsFromExchange>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        getFinnhubApiService().getStockSymbolFromExchange(exchange, TOKEN)
            .request(onSuccess, onError)
    }

    fun getProfile(
        symbol: String, onSuccess: (Profile) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        getFinnhubApiService().getCompany(symbol, TOKEN).request(onSuccess, onError)
    }

    fun getLogo(symbol: String): String {
        return "https://finnhub.io/api/logo?symbol=$symbol"
    }

    fun getCurrentPrices(
        onSuccess: (List<ResponsePriceAllStocksByDate>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val api = getMoexApiService()
        api.getPriceAllStocksLastDate(start = "0")
            .requestIo(
                { item1 ->
                    api.getPriceAllStocksLastDate(start = "100")
                        .requestIo({ item2 ->
                            api.getPriceAllStocksLastDate(start = "200")
                                .request({ item3 ->
                                    onSuccess(item1 + item2 + item3)
                                }, onError)
                        }, onError)
                }, onError
            )
    }

    fun getStocks(
        onSuccess: (List<Stock>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val api = getMoexApiService()
        val list = mutableListOf<Stock>()
        api.getAllStocks().requestIo({ item1 ->
            api.getAllStocks(start = "100").requestIo({ item2 ->
                api.getAllStocks(start = "200").request({ item3 ->
                    item3.forEach { e ->
                        if (e.securities != null) {
                            list.addAll(parseSecurities(e.securities))
                        }
                    }
                    onSuccess(list)
                }, onError)
                item2.forEach { e ->
                    if (e.securities != null) {
                        list.addAll(parseSecurities(e.securities))
                    }
                }
            }, onError)
            item1.forEach { e ->
                if (e.securities != null) {
                    list.addAll(parseSecurities(e.securities))
                }
            }
        }, onError)
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

private fun <T> Single<T>.requestIo(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
    this.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe({
            //TODO("Добавить обёртку для сохранения данных в db")
            onSuccess(it)
        }, {
            //TODO("Добавить обёртку для данных из db")
            onError(it)
        })
}