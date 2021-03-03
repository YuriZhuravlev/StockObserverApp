package com.zhuravlev.stockobserverapp.storage.net

import com.zhuravlev.stockobserverapp.model.Profile
import com.zhuravlev.stockobserverapp.model.ResponseSearchSymbol
import com.zhuravlev.stockobserverapp.model.ResponseSearchSymbolsFromExchange
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubApi {
    /**
     * Поиск акций по строке
     */
    @GET("search")
    fun getSymbolLookup(
        @Query("q") symbol: String,
        @Query("token") token: String
    ): Single<ResponseSearchSymbol>

    /**
     * Получение всех акций торгуемых на выбранной бирже
     *
     * [CHECK LIST](https://docs.google.com/spreadsheets/d/1I3pBxjfXB056-g_JYf_6o3Rns3BV2kMGG1nCatb91ls/edit#gid=0)
     */
    @GET("stock/symbol")
    fun getStockSymbolFromExchange(
        @Query("exchange") exchange: String,
        @Query("token") token: String
    ): Single<List<ResponseSearchSymbolsFromExchange>>

    @GET("stock/profile2")
    fun getCompany(@Query("symbol") symbol: String, @Query("token") token: String): Single<Profile>
}