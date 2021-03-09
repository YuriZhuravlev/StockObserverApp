package com.zhuravlev.stockobserverapp.storage.net

import com.zhuravlev.stockobserverapp.model.moex.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoexApi {
    /**
     * history/engines/stock/markets/shares/boards/tqbr/securities.json?date=2021-03-04&iss.meta=off&iss.json=compact&start=100&limit=5
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

    /**
     * history/engines/stock/markets/shares/boards/tqbr/securities.json?iss.meta=off&iss.json=compact&start=100&limit=5
     */
    @GET("history/engines/stock/markets/shares/boards/{board_param}/securities.json")
    fun getPriceAllStocksLastDate(
        @Path("board_param") board: String = "tqbr",
        @Query("start") start: String = "0",
        @Query("limit") limit: String = "100",
        @Query("iss.json") json: String = "extended",
        @Query("iss.meta") meta: String = "off"
    ): Single<List<ResponsePriceAllStocksByDate>>

    /**
     * Список торгуемых акций:
     * https://iss.moex.com/iss/history/engines/stock/markets/shares/boards/tqbr/listing?status=traded&start=0
     */
    @GET("history/engines/stock/markets/shares/boards/{board_param}/listing.json")
    fun getAllStocks(
        @Path("board_param") board: String = "tqbr",
        @Query("start") start: String = "0",
        @Query("status") status: String = "traded",
        @Query("iss.json") json: String = "extended",
        @Query("iss.meta") meta: String = "off"
    ): Single<List<ResponseAllStocks>>


    /**
     * Список цен за временный период (сутки):
     * https://iss.moex.com/iss/engines/stock/markets/shares/boardgroups/tqbr/securities/ABRD/candles?from=2020-01-01&interval=24
     */
    @GET("engines/stock/markets/shares/boardgroups/{board_param}/securities/{symbol}/candles.json")
    fun getPricesBySymbol(
        @Path("symbol") symbol: String,
        @Path("board_param") board: String = "tqbr",
        @Query("from") from: String = "2020-01-01",
        @Query("interval") interval: String = "24",
        @Query("iss.json") json: String = "extended",
        @Query("iss.meta") meta: String = "off"
    ): Single<List<ResponsePricesBySymbol>>

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
}