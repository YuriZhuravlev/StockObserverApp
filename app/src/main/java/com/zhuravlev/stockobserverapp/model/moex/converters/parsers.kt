package com.zhuravlev.stockobserverapp.model.moex.converters

import com.zhuravlev.stockobserverapp.model.PriceChart
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.model.moex.ResponseCandles
import com.zhuravlev.stockobserverapp.model.moex.ResponseMarketData
import com.zhuravlev.stockobserverapp.model.moex.Security
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Callable

fun parseSecurityList(response: List<Security?>): Single<MutableList<Stock>> {
    return Single.fromCallable {
        val list = mutableListOf<Stock>()
        response.forEach {
            if (it != null && it.prevWaPrice != null) {
                list.add(
                    Stock(
                        it.secId!!,
                        "https://finnhub.io/api/logo?symbol=${it.secId}.ME",
                        it.secName ?: it.shortName ?: "",
                        false,
                        it.prevWaPrice.toString(),
                        "",
                        it.boardId ?: "",
                        it.latName ?: ""
                    )
                )
            }
        }
        return@fromCallable list
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

/**
 * @return Map<symbol, Pair<currentPrice, lastChange>>
 */
fun parseResponseMarketData(response: List<ResponseMarketData>): Single<Map<String, Pair<String, String>>> {
    return Single.fromCallable(Callable {
        val map = mutableMapOf<String, Pair<String, String>>()
        response.forEach {
            if (it.marketdata != null) {
                it.marketdata.forEach { item ->
                    if (item?.secId != null && item.change != null && item.lCurrentPrice != null) {
                        map[item.secId] = Pair(item.lCurrentPrice, item.change)
                    }
                }
            }
        }
        return@Callable map.toMap()
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}


fun parseResponseCandles(response: List<ResponseCandles>, stock: Stock): Single<PriceChart> {
    return Single.fromCallable {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val priceChart = PriceChart(stock)

        response.forEach {
            it.candles?.forEach { candle ->
                if (candle?.begin != null && candle.high != null && candle.low != null && candle.volume != null) {
                    val date: Date? = dateFormat.parse(candle.begin)
                    if (date != null) {
                        priceChart.list.add(
                            PriceChart.Item(
                                candle.low,
                                candle.high,
                                candle.volume,
                                date
                            )
                        )
                    }
                }
            }
        }

        return@fromCallable priceChart
    }
}