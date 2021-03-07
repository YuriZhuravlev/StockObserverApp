package com.zhuravlev.stockobserverapp.storage.net

import com.zhuravlev.stockobserverapp.model.moex.ResponsePriceAllStocksByDate
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoexApi {
    // history/engines/stock/markets/shares/boards/tqbr/securities.json?date=2021-03-04&iss.meta=off&iss.json=compact&start=100&limit=5
    /**
     * @param date = YYYY-MM-DD
     */
    @GET("history/engines/stock/markets/shares/boards/tqbr/securities.json")
    fun getPriceAllStocksByDate(
        @Query("date") date: String,
        @Query("start") start: String = "0",
        @Query("limit") limit: String = "100",
        @Query("iss.json") json: String = "extended",
        @Query("iss.meta") meta: String = "off"
    ): Single<List<ResponsePriceAllStocksByDate>>

    // history/engines/stock/markets/shares/boards/tqbr/securities.json?iss.meta=off&iss.json=compact&start=100&limit=5
    @GET("history/engines/stock/markets/shares/boards/tqbr/securities.json")
    fun getPriceAllStocksLastDate(
        @Query("start") start: String = "0",
        @Query("limit") limit: String = "100",
        @Query("iss.json") json: String = "extended",
        @Query("iss.meta") meta: String = "off"
    ): Single<List<ResponsePriceAllStocksByDate>>
}