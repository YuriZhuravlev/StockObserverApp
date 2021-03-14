package com.zhuravlev.stockobserverapp.storage.net

import com.zhuravlev.stockobserverapp.model.moex.ResponseCandles
import com.zhuravlev.stockobserverapp.model.moex.ResponseMarketData
import com.zhuravlev.stockobserverapp.model.moex.ResponseSecurities
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoexApi {
    /**
     * Список текущих акций (описание и цена)
     * https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities?iss.only=securities
     */
    @GET("engines/stock/markets/shares/boards/TQBR/securities.json")
    fun getSecurities(
        @Query("iss.only") only: String = "securities",
        @Query("iss.json") json: String = "extended",
        @Query("iss.meta") meta: String = "off"
    ): Single<List<ResponseSecurities>>


    /**
     * Список текущих цен (подробно)
     * https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities?iss.only=marketdata
     */
    @GET("engines/stock/markets/shares/boards/TQBR/securities.json")
    fun getMarketData(
        @Query("iss.only") only: String = "marketdata",
        @Query("iss.json") json: String = "extended",
        @Query("iss.meta") meta: String = "off"
    ): Single<List<ResponseMarketData>>

    // engines/stock/markets/shares/boardgroups/tqbr/securities/ABRD/candles?from=2020-01-01&interval=24
    @GET("engines/stock/markets/shares/boardgroups/tqbr/securities/{symbol}/candles.json")
    fun getCandles(
        @Path("symbol") symbol: String,
        @Query("from") from: String,
        @Query("interval") interval: String = "24",
        @Query("iss.only") only: String = "marketdata",
        @Query("iss.json") json: String = "extended",
        @Query("iss.meta") meta: String = "off"
    ): Single<List<ResponseCandles>>
}