package com.zhuravlev.stockobserverapp.storage

import com.zhuravlev.stockobserverapp.model.Profile
import com.zhuravlev.stockobserverapp.model.ResponseSearchSymbol
import com.zhuravlev.stockobserverapp.model.ResponseSearchSymbolsFromExchange
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.model.moex.ResponsePriceAllStocksByDate
import com.zhuravlev.stockobserverapp.storage.net.EXCHANGE_POSTFIX_FINNHUB
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
        getMoexApiService().getPriceAllStocksLastDate("0").request(
            { item1 ->
                getMoexApiService().getPriceAllStocksLastDate("100").request({ item2 ->
                    getMoexApiService().getPriceAllStocksLastDate("200").request({ item3 ->
                        onSuccess(item1 + item2 + item3)
                    }, onError)
                }, onError)
            }, onError
        )
    }

    fun getStocks(
        onSuccess: (List<Stock>) -> Unit,
        onError: (Throwable) -> Unit
    ): MutableList<Stock> {
        val list = mutableListOf<Stock>()
        getStocksFromExchange("ME", {
            for (i in 0..it.lastIndex) {
                list.add(
                    Stock(
                        it[i].symbol!!.removeSuffix(EXCHANGE_POSTFIX_FINNHUB),
                        getLogo(it[i].symbol!!),
                        it[i].description!!,
                        false,
                        "",
                        ""
                    )
                )
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