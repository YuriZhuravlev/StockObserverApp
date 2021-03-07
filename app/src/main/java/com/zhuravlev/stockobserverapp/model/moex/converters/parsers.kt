package com.zhuravlev.stockobserverapp.model.moex.converters

import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.model.moex.ResponsePriceAllStocksByDate
import com.zhuravlev.stockobserverapp.model.moex.SecuritiesItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Callable

/*
                0           1           2           3           4           5       6       7   8           9                   10      11      12          13                  14          15                  16          17                          18              19      20
"columns": ["BOARDID", "TRADEDATE", "SHORTNAME", "SECID", "NUMTRADES", "VALUE", "OPEN", "LOW", "HIGH", "LEGALCLOSEPRICE", "WAPRICE", "CLOSE", "VOLUME", "MARKETPRICE2", "MARKETPRICE3", "ADMITTEDQUOTE", "MP2VALTRD", "MARKETPRICE3TRADESVALUE", "ADMITTEDVALUE", "WAVAL", "TRADINGSESSION"],
["TQBR", "2021-03-05", "АбрауДюрсо", "ABRD", 1661, 19995360, 212, 208.5, 218, 211, 214, 211, 93450, 214, 214, 211, 19995360, 19995360, 19995360, 0, 3],
 */

/**
 * Парсинг результата, сейчас берутся цены на момент открытия и закрытия биржи - эта пара кладётся по symbol акции
 * @return Map<symbol, Pair<openPrice, closePrice>>
 */
fun parseResponsePriceAllStocksByDate(response: List<ResponsePriceAllStocksByDate>): Single<Map<String, Pair<String, String>>> {
    return Single.fromCallable(Callable {
        val map = mutableMapOf<String, Pair<String, String>>()
        response.forEach {
            if (it.history != null) {
                it.history.forEach { historyItem ->
                    if (historyItem != null && historyItem.symbol != null && historyItem.open != null && historyItem.close != null) {
                        map[historyItem.symbol] = Pair(historyItem.open, historyItem.close)
                    }
                }
            }
        }
        return@Callable map.toMap()
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}


fun parseSecurities(response: List<SecuritiesItem?>): MutableList<Stock> {
    val list = mutableListOf<Stock>()
    response.forEach {
        if (it != null && it.secId != null && it.boardId != null) {
            list.add(
                Stock(
                    it.secId,
                    "https://finnhub.io/api/logo?symbol=${it.secId}.ME",
                    it.name ?: it.shortName ?: "",
                    false,
                    "",
                    "",
                    it.boardId
                )
            )
        }
    }
    return list
}