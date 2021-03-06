package com.zhuravlev.stockobserverapp.model.moex.converters

import com.zhuravlev.stockobserverapp.model.moex.ResponsePriceAllStocksByDate

/*
                0           1           2           3           4           5       6       7   8           9                   10      11      12          13                  14          15                  16          17                          18              19      20
"columns": ["BOARDID", "TRADEDATE", "SHORTNAME", "SECID", "NUMTRADES", "VALUE", "OPEN", "LOW", "HIGH", "LEGALCLOSEPRICE", "WAPRICE", "CLOSE", "VOLUME", "MARKETPRICE2", "MARKETPRICE3", "ADMITTEDQUOTE", "MP2VALTRD", "MARKETPRICE3TRADESVALUE", "ADMITTEDVALUE", "WAVAL", "TRADINGSESSION"],
["TQBR", "2021-03-05", "АбрауДюрсо", "ABRD", 1661, 19995360, 212, 208.5, 218, 211, 214, 211, 93450, 214, 214, 211, 19995360, 19995360, 19995360, 0, 3],
 */

/**
 * Парсинг результата, сейчас берутся цены на момент открытия и закрытия биржи - эта пара кладётся по symbol акции
 * @return Map<symbol, Pair<openPrice, closePrice>>
 */
fun parseResponsePriceAllStocksByDate(response: ResponsePriceAllStocksByDate): Map<String, Pair<String, String>> {
    val map = mutableMapOf<String, Pair<String, String>>()
//    response.history?.data?.forEach {
//        if (it != null) {
//            val list = it.replace("\"", "").split(", ")
//            map[list[3]] = Pair(list[6], list[11])
//        }
//    }
    return map
}