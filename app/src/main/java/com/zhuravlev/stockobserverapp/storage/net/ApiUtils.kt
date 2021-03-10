package com.zhuravlev.stockobserverapp.storage.net

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val SERVER_URL_MOEX = "https://iss.moex.com/iss/"
const val EXCHANGE_POSTFIX_FINNHUB = ".ME"
const val EXCHANGE = "MOEX"
val boardList = listOf("tqbr", "tqtd", "smal", "tqpi", "tqte", "tqtf")

private var retrofit: Retrofit? = null
private val gson = Gson()
private var api: MoexApi? = null

fun getRetrofitMoex(): Retrofit {
    if (retrofit == null) {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL_MOEX)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    return retrofit!!
}


fun getMoexApiService(): MoexApi {
    if (api == null) {
        api = getRetrofitMoex().create(MoexApi::class.java)
    }
    return api!!
}
